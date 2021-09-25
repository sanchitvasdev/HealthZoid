package com.sanchit.healthzoid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealItemDao{
    @Insert
    fun insertItem(mealItem: MealItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg mealItems: MealItem)

    @Query("SELECT * FROM meal_item_table WHERE itemTitle = :key")
    fun getItem(key: String): MealItem

    @Query("SELECT * FROM meal_item_table WHERE itemTitle = :key")
    fun getItemWithTitle(key: String): LiveData<MealItem>

    @Query("SELECT * FROM meal_item_table")
    fun getMealItems(): LiveData<List<MealItem>>

    @Query("SELECT * FROM meal_item_table")
    fun getItemWithTypedString(): LiveData<List<MealItem>>

//    @Query("SELECT * FROM meal_item_table WHERE itemTitle LIKE :search ORDER BY itemTitle ASC")
//    fun getItemWithTypedString(): LiveData<List<MealItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBreakfast(vararg breakfastMealItems: BreakfastMealItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreakfastMealItem(breakfastMealItem: BreakfastMealItem)

    @Query("SELECT * FROM breakfast_meal_item_table")
    fun getBreakfastMealItems(): LiveData<List<BreakfastMealItem>>

    @Query("SELECT * FROM breakfast_meal_item_table WHERE itemTitle = :key")
    fun getBreakfastItem(key: String): BreakfastMealItem

    @Query("DELETE FROM breakfast_meal_item_table WHERE itemTitle = :key")
    fun clearBreakfastMealItem(key: String)

    @Query("DELETE FROM breakfast_meal_item_table")
    fun clearAllBreakfast()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLunch(vararg lunchMealItems: LunchMealItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLunchMealItem(lunchMealItem: LunchMealItem)

    @Query("SELECT * FROM lunch_meal_item_table")
    fun getLunchMealItems(): LiveData<List<LunchMealItem>>

    @Query("SELECT * FROM lunch_meal_item_table WHERE itemTitle = :key")
    fun getLunchItem(key: String): LunchMealItem

    @Query("DELETE FROM lunch_meal_item_table WHERE itemTitle = :key")
    fun clearLunchMealItem(key: String)

    @Query("DELETE FROM lunch_meal_item_table")
    fun clearAllLunch()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSnacks(vararg snacksMealItems: SnacksMealItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSnacksMealItem(snacksMealItem: SnacksMealItem)

    @Query("SELECT * FROM snacks_meal_item_table")
    fun getSnacksMealItems(): LiveData<List<SnacksMealItem>>

    @Query("SELECT * FROM snacks_meal_item_table WHERE itemTitle = :key")
    fun getSnacksItem(key: String): SnacksMealItem

    @Query("DELETE FROM snacks_meal_item_table WHERE itemTitle = :key")
    fun clearSnacksMealItem(key: String)

    @Query("DELETE FROM snacks_meal_item_table")
    fun clearAllSnacks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDinner(vararg dinnerMealItems: DinnerMealItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDinnerMealItem(dinnerMealItem: DinnerMealItem)

    @Query("SELECT * FROM dinner_meal_item_table")
    fun getDinnerMealItems(): LiveData<List<DinnerMealItem>>

    @Query("SELECT * FROM dinner_meal_item_table WHERE itemTitle = :key")
    fun getDinnerItem(key: String): DinnerMealItem

    @Query("DELETE FROM dinner_meal_item_table WHERE itemTitle = :key")
    fun clearDinnerMealItem(key: String)

    @Query("DELETE FROM dinner_meal_item_table")
    fun clearAllDinner()
}