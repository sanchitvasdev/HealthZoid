package com.sanchit.healthzoid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sanchit.healthzoid.database.*
import kotlinx.coroutines.*

class CommonViewModel(application: Application): AndroidViewModel(application){
    val database = MealItemDatabase.getInstance(application)

    private val job = SupervisorJob()

    private val scope = CoroutineScope(job + Dispatchers.Main)
    
    private var _breakfastmealItemsList = MutableLiveData<MutableSet<BreakfastMealItem>>()
    val breakfastmealItemsList: LiveData<MutableSet<BreakfastMealItem>>
        get() = _breakfastmealItemsList

    private var _lunchmealItemsList = MutableLiveData<MutableSet<LunchMealItem>>()
    val lunchmealItemsList: LiveData<MutableSet<LunchMealItem>>
        get() = _lunchmealItemsList

    private var _snacksmealItemsList = MutableLiveData<MutableSet<SnacksMealItem>>()
    val snacksmealItemsList: LiveData<MutableSet<SnacksMealItem>>
        get() = _snacksmealItemsList

    private var _dinnermealItemsList = MutableLiveData<MutableSet<DinnerMealItem>>()
    val dinnermealItemsList: LiveData<MutableSet<DinnerMealItem>>
        get() = _dinnermealItemsList

    fun updateRecyclerViews(){
        scope.launch {
            database.mealItemDao.getBreakfastMealItems().observeForever {
                if(_breakfastmealItemsList.value==null){
                    _breakfastmealItemsList.value = it.toMutableSet()
                }else{
                    _breakfastmealItemsList.value!!.addAll(it.toMutableSet())
                }
            }
            database.mealItemDao.getLunchMealItems().observeForever {
                if(_lunchmealItemsList.value==null){
                    _lunchmealItemsList.value = it.toMutableSet()
                }else{
                    _lunchmealItemsList.value!!.addAll(it.toMutableSet())
                }
            }

            database.mealItemDao.getSnacksMealItems().observeForever {
                if(_snacksmealItemsList.value==null){
                    _snacksmealItemsList.value = it.toMutableSet()
                }else{
                    _snacksmealItemsList.value!!.addAll(it.toMutableSet())
                }
            }

            database.mealItemDao.getDinnerMealItems().observeForever {
                if(_dinnermealItemsList.value==null){
                    _dinnermealItemsList.value = it.toMutableSet()
                }else{
                    _dinnermealItemsList.value!!.addAll(it.toMutableSet())
                }
            }
        }
    }

    fun clearRecyclerViews(){
        _breakfastmealItemsList.value = null
        _lunchmealItemsList.value = null
        _snacksmealItemsList.value = null
        _dinnermealItemsList.value = null
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}