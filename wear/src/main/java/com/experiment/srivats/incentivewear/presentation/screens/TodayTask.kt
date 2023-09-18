//package com.experiment.srivats.incentivewear.presentation.screens
//
//import android.util.Log
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.navigation.NavController
//import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
//import androidx.wear.compose.foundation.lazy.items
//import androidx.wear.compose.material.Chip
//import androidx.wear.compose.material.ChipDefaults
//import androidx.wear.compose.material.MaterialTheme
//import androidx.wear.compose.material.Scaffold
//import androidx.wear.compose.material.Text
//import androidx.wear.compose.material.TimeText
//import androidx.wear.compose.material.Vignette
//import androidx.wear.compose.material.VignettePosition
//import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskModelItem
//import com.experiment.srivats.incentivewear.presentation.helper.vm.CurrVM
//
//@Composable
//fun TodayTask(vm: CurrVM){
//    Log.d("TodayTask.kt", vm.currList.toString())
//    Scaffold(
//        timeText = { TimeText() },
//        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
//    ) {
//        ScalingLazyColumn(){
//            items(vm.currList){cl -> TodayTaskChip(taskModel = cl)}
//        }
//    }
//}
//
//@Composable
//fun TodayTaskChip(taskModel: TaskModelItem){
//    println("Task Model: $taskModel")
//    Chip(
//        modifier = Modifier.fillMaxWidth(),
//        onClick = {  },
//        label = {
//            Text(
//                text = taskModel.taskName,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//        },
//        colors = ChipDefaults.chipColors(
//            backgroundColor = MaterialTheme.colors.secondary
//        )
//    )
//}