package com.experiment.srivats.incentivewear.presentation.helper

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/db/getUserInfo")
    fun getData(@Query("uuid") uuid: String): Call<List<ApiDataModelItem>>
}