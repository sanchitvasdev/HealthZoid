package com.sanchit.healthzoid.foods

import android.app.Application
import androidx.lifecycle.*
import com.sanchit.healthzoid.database.MealItem
import com.sanchit.healthzoid.database.MealItemDatabase
import com.sanchit.healthzoid.databinding.MealitemBinding
import com.sanchit.healthzoid.repository.MealItemsRepository
import kotlinx.coroutines.*

class FoodsViewModel(private val timeofFood: Long,application: Application): AndroidViewModel(application){
    private val Job = SupervisorJob()
    private val scope = CoroutineScope(Job + Dispatchers.Main)

    private val database = MealItemDatabase.getInstance(application)
    private val repository = MealItemsRepository(database)

    init {
        scope.launch {
            repository.refreshMealItems()
        }
    }

    val mealItems = repository.mealItems

    private var _breakfastmealItemsList = MutableLiveData<MutableSet<MealItem>>()
    val breakfastmealItemsList: LiveData<MutableSet<MealItem>>
        get() = _breakfastmealItemsList

    private var _lunchmealItemsList = MutableLiveData<MutableSet<MealItem>>()
    val lunchmealItemsList: LiveData<MutableSet<MealItem>>
        get() = _lunchmealItemsList

    private var _snacksmealItemsList = MutableLiveData<MutableSet<MealItem>>()
    val snacksmealItemsList: LiveData<MutableSet<MealItem>>
        get() = _snacksmealItemsList

    private var _dinnermealItemsList = MutableLiveData<MutableSet<MealItem>>()
    val dinnermealItemsList: LiveData<MutableSet<MealItem>>
        get() = _dinnermealItemsList

    var add = mutableSetOf<MealItem>()

    fun onMealItemClicked(title: String){
        lateinit var mealItemToAdd: MealItem
        scope.launch {
           mealItemToAdd = getMealItemfromdatabase(title)
            add.add(mealItemToAdd)
            if(timeofFood==0L){
                _breakfastmealItemsList.value = add
            }else if(timeofFood==1L){
                _lunchmealItemsList.value = add
            }else if(timeofFood==2L){
                _snacksmealItemsList.value = add
            }else {
                _dinnermealItemsList.value = add
            }
        }
    }

    private var _navigateToDailyDietFragment = MutableLiveData<Boolean>(false)
    val navigateToDailyDietFragment: LiveData<Boolean>
        get() = _navigateToDailyDietFragment

    fun onAddButtonClicked(){
        _navigateToDailyDietFragment.value = true
    }

    fun onNavigated(){
        _navigateToDailyDietFragment.value = false
    }

    private suspend fun getMealItemfromdatabase(title: String): MealItem {
        return withContext(Dispatchers.IO){
            val mealItem = database.mealItemDao.getItem(title)
            mealItem
        }
    }

    override fun onCleared() {
        super.onCleared()
        Job.cancel()
    }
}