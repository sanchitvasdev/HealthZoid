package com.sanchit.healthzoid.waterintake

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanchit.healthzoid.foods.FoodsViewModel
import java.lang.IllegalArgumentException

class WaterIntakeViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterIntakeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return WaterIntakeViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }
}