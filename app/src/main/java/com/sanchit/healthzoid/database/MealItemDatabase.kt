package com.sanchit.healthzoid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealItem::class,BreakfastMealItem::class,LunchMealItem::class,SnacksMealItem::class,DinnerMealItem::class],version = 3,exportSchema = false)
abstract class MealItemDatabase : RoomDatabase() {
    abstract val mealItemDao: MealItemDao

    companion object{

        @Volatile   // Written to update the database if the data changes
        private lateinit var INSTANCE: MealItemDatabase

        fun getInstance(context: Context) :MealItemDatabase {
            synchronized(this){  // to allow only one thread to run
                if(!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MealItemDatabase::class.java,
                            "meal_item_database"
                    )
                            .fallbackToDestructiveMigration() // if the structure of the table changes it will destroy the database and copy paste into a new database
                            .build()
                }
            }
            return INSTANCE
        }
    }
}