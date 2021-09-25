package com.sanchit.healthzoid.volumecalculator

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class VolumeCalculatorViewModel(application: Application): AndroidViewModel(application){
    private var _volumeValue = MutableLiveData<Float>()
    val volumeValue: LiveData<Float>
        get() = _volumeValue

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences("volumeDetails",Context.MODE_PRIVATE)
        _volumeValue.value = sharedPreferences.getFloat("volumeValue",0f)
    }

    fun store(value: Float){
        _volumeValue.value = value
    }
}