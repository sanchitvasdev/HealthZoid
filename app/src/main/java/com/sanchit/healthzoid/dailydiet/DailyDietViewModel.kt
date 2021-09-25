package com.sanchit.healthzoid.dailydiet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.database.MealItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyDietViewModel(application: Application) : AndroidViewModel(application) {

    val resources = getApplication<Application>().applicationContext.resources

    private var _calories = MutableLiveData<Double>()
    val calories: LiveData<String>
        get() = _caloriesString

    private var _protein = MutableLiveData<Double>()
    val protein: LiveData<String>
        get() = _proteinString

    private var _carbs = MutableLiveData<Double>()
    val carbs: LiveData<String>
        get() = _carbsString

    private var _fats = MutableLiveData<Double>()
    val fats: LiveData<String>
        get() = _fatsString

    private var _caloriesString = MutableLiveData<String>()
    private var _proteinString = MutableLiveData<String>()
    private var _carbsString = MutableLiveData<String>()
    private var _fatsString = MutableLiveData<String>()

    private var _caloriesProgress = MutableLiveData<Int>()
    val caloriesProgress: LiveData<Int>
    get() = _caloriesProgress

    private var _proteinProgress = MutableLiveData<Int>()
    val proteinProgress: LiveData<Int>
        get() = _proteinProgress

    private var _carbsProgress = MutableLiveData<Int>()
    val carbsProgress: LiveData<Int>
        get() = _carbsProgress

    private var _fatsProgress = MutableLiveData<Int>()
    val fatsProgress: LiveData<Int>
        get() = _fatsProgress

    private var _caloriesLimit = MutableLiveData<Boolean>()
    val caloriesLimit: LiveData<Boolean>
        get() = _caloriesLimit

    private var _proteinLimit = MutableLiveData<Boolean>()
    val proteinLimit: LiveData<Boolean>
        get() = _proteinLimit

    private var _carbsLimit = MutableLiveData<Boolean>()
    val carbsLimit: LiveData<Boolean>
        get() = _carbsLimit

    private var _fatsLimit = MutableLiveData<Boolean>()
    val fatsLimit: LiveData<Boolean>
        get() = _fatsLimit

    init {
        _calories.value = 0.0
        _protein.value = 0.0
        _carbs.value = 0.0
        _fats.value = 0.0

        _caloriesString.value = resources.getString(R.string.total_kcals_text,_calories.value)
        _proteinString.value = resources.getString(R.string.protein_grams_text,_protein.value)
        _carbsString.value = resources.getString(R.string.carbs_grams_text,_carbs.value)
        _fatsString.value = resources.getString(R.string.fats_grams_text,_fats.value)

        _caloriesProgress.value = 0
        _proteinProgress.value = 0
        _carbsProgress.value = 0
        _fatsProgress.value = 0

        _caloriesLimit.value = false
        _proteinLimit.value = false
        _carbsLimit.value = false
        _fatsLimit.value = false
    }

    fun reset(){
        _calories.value = 0.0
        _protein.value = 0.0
        _carbs.value = 0.0
        _fats.value = 0.0

        _caloriesString.value = resources.getString(R.string.total_kcals_text,_calories.value)
        _proteinString.value = resources.getString(R.string.protein_grams_text,_protein.value)
        _carbsString.value = resources.getString(R.string.carbs_grams_text,_carbs.value)
        _fatsString.value = resources.getString(R.string.fats_grams_text,_fats.value)

        _caloriesProgress.value = 0
        _proteinProgress.value = 0
        _carbsProgress.value = 0
        _fatsProgress.value = 0
    }

    fun update(cal: Double,protein: Double,carbs: Double,fats: Double){
        _calories.value = _calories.value?.plus(cal)
        _caloriesString.value = resources.getString(R.string.total_kcals_text,_calories.value)
        _caloriesProgress.value = _caloriesProgress.value?.plus((cal/35).toInt())

        _protein.value = _protein.value?.plus(protein)
        _proteinString.value = resources.getString(R.string.protein_grams_text,_protein.value)
        _proteinProgress.value = _proteinProgress.value?.plus((protein/2.5).toInt())

        _carbs.value = _carbs.value?.plus(carbs)
        _carbsString.value = resources.getString(R.string.carbs_grams_text,_carbs.value)
        _carbsProgress.value = _carbsProgress.value?.plus((carbs/4.0).toInt())

        _fats.value = _fats.value?.plus(fats)
        _fatsString.value = resources.getString(R.string.fats_grams_text,_fats.value)
        _fatsProgress.value = _fatsProgress.value?.plus(fats.toInt())

        _caloriesLimit.value = _calories.value!! >3500.0
        _proteinLimit.value = _protein.value!!>250.0
        _carbsLimit.value = _carbs.value!!>400.0
        _fatsLimit.value = _fats.value!!>100.0
    }

    fun clearAll(application: Application){
        val database = MealItemDatabase.getInstance(application)
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                database.mealItemDao.clearAllBreakfast()
                database.mealItemDao.clearAllLunch()
                database.mealItemDao.clearAllSnacks()
                database.mealItemDao.clearAllDinner()
            }
        }
    }

    private var _visibility = MutableLiveData<Boolean>(false)
    val visibility: LiveData<Boolean>
        get() = _visibility

    fun visibilitychange(){
        _visibility.value = !_visibility.value!!
    }
}