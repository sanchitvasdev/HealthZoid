package com.sanchit.healthzoid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sanchit.healthzoid.database.MealItemDatabase
import com.sanchit.healthzoid.domain.MealItemModel
import com.sanchit.healthzoid.network.MealApi
import com.sanchit.healthzoid.network.asDatabaseModel
import com.sanchit.healthzoid.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealItemsRepository (private val database: MealItemDatabase){

    val mealItems: LiveData<List<MealItemModel>> = Transformations.map(database.mealItemDao.getMealItems()){
        it.asDomainModel()
    }

    suspend fun refreshMealItems(){
        withContext(Dispatchers.IO){
            val palette = MealApi.retrofitService.getMeals().await()
            database.mealItemDao.insertAll(*palette.asDatabaseModel())
        }
    }
}