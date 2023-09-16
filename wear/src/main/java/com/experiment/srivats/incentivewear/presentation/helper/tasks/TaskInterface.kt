package com.experiment.srivats.incentivewear.presentation.helper.tasks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TaskInterface {
    @GET("/api/db/getTodaysTask")
    fun getCurrentTask(@Query("userId") userId: String): Call<List<TaskModelItem>>
}