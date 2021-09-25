package com.sanchit.healthzoid.yourbmrandtee

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class YourBmrAndTeeViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(YourBmrAndTeeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return YourBmrAndTeeViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }

}