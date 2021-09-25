package com.sanchit.healthzoid.reviewus

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sanchit.healthzoid.R

class ReviewUsViewModel(application: Application): AndroidViewModel(application){

    private var _numOfWords = MutableLiveData<Int>()
    val numOfWords: LiveData<Int>
        get() = _numOfWords

    init {
        _numOfWords.value = 0
        val sharedPreferences = application.applicationContext.getSharedPreferences("ReviewDetails",Context.MODE_PRIVATE)
        _numOfWords.value = sharedPreferences.getInt("numOfWords",0)
    }

    fun store(value: Int){
        _numOfWords.value = value
    }
}