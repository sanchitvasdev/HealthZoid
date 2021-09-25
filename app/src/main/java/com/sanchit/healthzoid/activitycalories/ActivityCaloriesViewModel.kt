package com.sanchit.healthzoid.activitycalories

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ActivityCaloriesViewModel(application: Application): AndroidViewModel(application){

    private var _activityCaloriesValue = MutableLiveData<Float>()
    val activityCaloriesValue: LiveData<Float>
        get() = _activityCaloriesValue

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences("activityDetails",Context.MODE_PRIVATE)
        _activityCaloriesValue.value = sharedPreferences.getFloat("activityCaloriesValue",0f)
    }

    fun store(value: Float){
        _activityCaloriesValue.value = value
    }
}