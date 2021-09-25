package com.sanchit.healthzoid.goalcalories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GoalCaloriesViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GoalCaloriesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return GoalCaloriesViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }

}