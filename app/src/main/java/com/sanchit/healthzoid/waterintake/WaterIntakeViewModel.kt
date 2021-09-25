package com.sanchit.healthzoid.waterintake

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WaterIntakeViewModel(application: Application): AndroidViewModel(application) {
    private var _waterIntake = MutableLiveData<Long>()
    val waterIntake: LiveData<Long>
        get() = _waterIntake

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences("waterIntake",Context.MODE_PRIVATE)
        _waterIntake.value = sharedPreferences.getLong("waterIntakeValue", 0L)
    }

    fun change(watervalue: Long){
        _waterIntake.value = watervalue
    }

    fun clear(){
        _waterIntake.value = 0
    }
}