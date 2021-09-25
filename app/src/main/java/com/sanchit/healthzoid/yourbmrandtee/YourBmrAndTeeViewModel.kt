package com.sanchit.healthzoid.yourbmrandtee

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class YourBmrAndTeeViewModel(application: Application): AndroidViewModel(application){

    private var _bmrValue = MutableLiveData<Float>()
    val bmrValue: LiveData<Float>
        get() = _bmrValue

    private var _teeValue = MutableLiveData<Float>()
    val teeValue: LiveData<Float>
        get() = _teeValue

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences("bmrAndTeeDetails",Context.MODE_PRIVATE)
        _bmrValue.value = sharedPreferences.getFloat("bmr",0f)
        _teeValue.value = sharedPreferences.getFloat("tee",0f)
    }

    fun store(valuebmr: Float,valuetee: Float){
        _bmrValue.value = valuebmr
        _teeValue.value = valuetee
    }
}