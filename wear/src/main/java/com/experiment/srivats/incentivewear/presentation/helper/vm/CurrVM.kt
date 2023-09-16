package com.experiment.srivats.incentivewear.presentation.helper.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.experiment.srivats.incentivewear.presentation.BASE_URL
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskInterface
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrVM(private val userId:String): ViewModel() {
    private val _currList = mutableListOf<TaskModelItem>()
    val currList: List<TaskModelItem>
        get() = _currList

    fun getCurrTasks(){
        println("CurrVM: $userId")
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TaskInterface::class.java)

        val retrofitData = retrofitBuilder.getCurrentTask("2i05I1H9t3NpR30qY19XkU7wDXF2")
        println("URL: ${retrofitData.request().url()}")
        retrofitData.enqueue(object : Callback<List<TaskModelItem>?> {
            override fun onResponse(
                call: Call<List<TaskModelItem>?>,
                response: Response<List<TaskModelItem>?>,
            ) {
                // not empty clear then add again
                if(_currList.isNotEmpty()){
                    _currList.clear()
                    _currList.addAll(response.body()!!)
                }
                else{
                    _currList.addAll(response.body()!!)
                }
                println("CurrVM: $_currList")
            }

            override fun onFailure(call: Call<List<TaskModelItem>?>, t: Throwable) {
                Log.e("VM Error", t.message.toString(), t)
            }
        })
    }
}