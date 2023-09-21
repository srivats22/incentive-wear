package com.experiment.srivats.incentivewear.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskModelItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun PendingTask(navController: NavController, userId: String){
    val listState = rememberScalingLazyListState()
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("M-dd-yyy")
    val formattedDate = formatter.format(today)
    val db = Firebase.firestore
    val taskList = remember { mutableStateListOf<TaskModelItem?>() }
    val isLoading = remember { mutableStateOf(true) }
    val isTaskEmpty = remember { mutableStateOf(false) }
    db.collection("users")
        .document(userId)
        .collection("tasks")
        .whereNotEqualTo("due", formattedDate)
        .get()
        .addOnSuccessListener { result ->
            if(!result.isEmpty){
                val list = result.documents
                for(d in list){
                    val tMI: TaskModelItem? = d.toObject(TaskModelItem::class.java)
                    taskList.add(tMI)
                }
                isLoading.value = false
            }
            if(result.isEmpty){
                isTaskEmpty.value = true
                isLoading.value = false
            }
        }
        .addOnFailureListener { exception ->
            Log.w("fStore", "Error getting documents.", exception)
        }
    Scaffold(
        timeText = { TimeText(modifier = Modifier.scrollAway(listState)) },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = listState
        ){
            if(isLoading.value){
                item{ CircularProgressIndicator() }
            }
            if(isTaskEmpty.value){
                item { Text("You have no pending tasks") }
            }
            else{
                itemsIndexed(taskList.distinct()){ index, item ->
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                                  navController.navigate(
                                      "taskDetails/${taskList[index]?.taskName}/${taskList[index]?.taskDescription}"
                                  )
                        },
                        label = {
                            taskList[index]?.taskName?.let {
                                Text(
                                    text = it,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color.DarkGray,
                        )
                    )
                }
            }
        }
    }
}
