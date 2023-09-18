package com.experiment.srivats.incentivewear.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.experiment.srivats.incentivewear.presentation.helper.CurrVMFactory
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskModel
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskModelItem
import com.experiment.srivats.incentivewear.presentation.helper.vm.CurrVM
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.job


@Composable
fun CurrTask(navController: NavController, userId: String){
    val db = Firebase.firestore
    var taskList = remember { mutableStateListOf<TaskModelItem?>() }
    var isLoading = remember { mutableStateOf(true) }
    db.collection("users")
        .document(userId)
        .collection("tasks")
        .whereEqualTo("due", "9-17-2023")
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
        }
        .addOnFailureListener { exception ->
            Log.w("fStore", "Error getting documents.", exception)
        }
    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(isLoading.value){
                CircularProgressIndicator()
            }
            else{
                Text("Build UI")
            }
        }
    }
}

fun getFStore(userId: String) {
    val db = Firebase.firestore
    var _taskList = mutableStateListOf<TaskModelItem>()
    db.collection("users")
        .document(userId)
        .collection("tasks")
        .whereEqualTo("due", "9-17-2023")
        .get()
        .addOnSuccessListener { result ->
            run {
                for (document in result) {
                    Log.d("fStore", "${document.id} => ${document.data}")
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("fStore", "Error getting documents.", exception)
        }
}
