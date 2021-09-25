package com.sanchit.healthzoid.foods

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FoodsViewModelFactory(private val timeofFood: Long,val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FoodsViewModel(timeofFood,app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }
}