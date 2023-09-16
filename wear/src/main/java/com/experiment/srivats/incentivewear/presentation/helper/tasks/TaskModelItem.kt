package com.experiment.srivats.incentivewear.presentation.helper.tasks

data class TaskModelItem(
    val due: String,
    val priority: Int,
    val taskDescription: String,
    val taskName: String,
    val taskReward: String
)