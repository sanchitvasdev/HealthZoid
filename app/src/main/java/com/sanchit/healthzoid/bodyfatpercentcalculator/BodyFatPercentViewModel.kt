package com.sanchit.healthzoid.bodyfatpercentcalculator

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BodyFatPercentViewModel(application: Application) : AndroidViewModel(application){
    private var _bodyFatValue = MutableLiveData<Float>()
    val bodyFatValue: LiveData<Float>
        get() = _bodyFatValue

    init {
        val sharedPreferences = application.applicationContext.getSharedPreferences("bodyFatDetails", Context.MODE_PRIVATE)
        _bodyFatValue.value = sharedPreferences.getFloat("bodyFatPercentage",0f)
    }

    fun store(bfpv: Float){
        _bodyFatValue.value = bfpv
    }
}