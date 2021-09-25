package com.sanchit.healthzoid.volumecalculator

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class VolumeCalculatorViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VolumeCalculatorViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VolumeCalculatorViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }

}