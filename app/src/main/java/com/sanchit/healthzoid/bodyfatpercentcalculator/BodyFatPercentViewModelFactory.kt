package com.sanchit.healthzoid.bodyfatpercentcalculator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BodyFatPercentViewModelFactory(val app: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BodyFatPercentViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return BodyFatPercentViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }

}