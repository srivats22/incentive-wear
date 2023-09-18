package com.experiment.srivats.incentivewear.presentation.helper.tasks

import com.experiment.srivats.incentivewear.presentation.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

data class TaskModelItem(
    val due: String? = "",
    val priority: Int? = 0,
    val taskDescription: String? = "",
    val taskName: String? = "",
    val taskReward: String? = ""
)