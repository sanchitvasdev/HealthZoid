package com.sanchit.healthzoid.goalcalories

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GoalCaloriesViewModel(application: Application) : AndroidViewModel(application){
    private var _goalCaloriesValue = MutableLiveData<Float>()
    val goalCaloriesValue: LiveData<Float>
        get() = _goalCaloriesValue

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences("goalCalories",Context.MODE_PRIVATE)
        _goalCaloriesValue.value = sharedPreferences.getFloat("goalCaloriesValue",0f)
    }

    fun store(value: Float){
        _goalCaloriesValue.value = value
    }
}