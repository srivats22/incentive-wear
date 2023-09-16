package com.experiment.srivats.incentivewear.presentation.helper.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInterface {

    @GET("api/db/getUserInfo")
    fun getData(@Query("uuid") uuid: String): Call<List<UserDataModelItem>>
}