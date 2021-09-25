package com.sanchit.healthzoid.reviewus

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ReviewUsViewModelFactory(val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReviewUsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ReviewUsViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to build View Model")
    }

}