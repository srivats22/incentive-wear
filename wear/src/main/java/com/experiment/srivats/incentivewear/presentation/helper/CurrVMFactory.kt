package com.experiment.srivats.incentivewear.presentation.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.experiment.srivats.incentivewear.presentation.helper.vm.CurrVM

class CurrVMFactory(private val userId:String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrVM::class.java)){
            return CurrVM(userId) as T
        }
        throw IllegalStateException()
    }
}