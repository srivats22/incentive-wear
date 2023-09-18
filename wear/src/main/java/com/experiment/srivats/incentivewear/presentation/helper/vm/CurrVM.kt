package com.experiment.srivats.incentivewear.presentation.helper.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.srivats.incentivewear.presentation.BASE_URL
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskInterface
import com.experiment.srivats.incentivewear.presentation.helper.tasks.TaskModelItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CurrVM(private val userId:String): ViewModel() {
    private val _taskLiveData = MutableLiveData<List<TaskModelItem>>()

    fun getTasks(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TaskInterface::class.java)
        val retrofitData = retrofitBuilder.getCurrentTask(userId)

        retrofitData.enqueue(object : Callback<List<TaskModelItem>?> {
            override fun onResponse(
                call: Call<List<TaskModelItem>?>,
                response: Response<List<TaskModelItem>?>,
            ) {
                if(response.body() != null){
                    _taskLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<TaskModelItem>?>, t: Throwable) {
                Log.e("VM Error", t.message.toString(), t)
            }
        })
    }

    fun observeTaskLiveData() : LiveData<List<TaskModelItem>> {
        return _taskLiveData
    }

//    private val _currList = mutableListOf<TaskModelItem>()
//    private val _todayTaskList: MutableLiveData<List<TaskModelItem>> = MutableLiveData()
//    var isError: Boolean by mutableStateOf(false)
//    var errorMsg: String by mutableStateOf("")
//    val currList: List<TaskModelItem>
//        get() = _currList
//
//    val todayTaskList: LiveData<List<TaskModelItem>> get() = _todayTaskList
//
//    fun getCurrTasks(){
//        println("CurrVM: $userId")
//        val retrofitBuilder = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(TaskInterface::class.java)
//        val retrofitData = retrofitBuilder.getCurrentTask(userId)
//
//        retrofitData.enqueue(object : Callback<List<TaskModelItem>?> {
//            override fun onResponse(
//                call: Call<List<TaskModelItem>?>,
//                response: Response<List<TaskModelItem>?>,
//            ) {
//                viewModelScope.launch {
//                    try{
//                        _currList.clear()
//                        _currList.addAll(response.body()!!)
//                        _todayTaskList.value = response.body()!!
//                    }
//                    catch (e: Exception){
//                        isError = true
//                        errorMsg = e.message.toString()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<TaskModelItem>?>, t: Throwable) {
//                isError = true
//                errorMsg = t.message.toString()
//            }
//        })
//    }
}