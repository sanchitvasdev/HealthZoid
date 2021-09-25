package com.sanchit.healthzoid.dailydiet

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DailyDietViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyDietViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DailyDietViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }
}