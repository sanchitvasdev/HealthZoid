package com.sanchit.healthzoid.activitycalories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ActivityCaloriesViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ActivityCaloriesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ActivityCaloriesViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }

}