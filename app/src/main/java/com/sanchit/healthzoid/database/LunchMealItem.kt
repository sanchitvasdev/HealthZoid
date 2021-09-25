package com.sanchit.healthzoid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lunch_meal_item_table")
data class LunchMealItem(
    @PrimaryKey
    var itemTitle: String = "Not titled",
    @ColumnInfo(name = "color")
    var itemColor: Int = 0,
    @ColumnInfo(name = "calories")
    var itemCalories: Double = 0.0,
    @ColumnInfo(name = "protein")
    var itemProtein: Double = 0.0,
    @ColumnInfo(name = "carbs")
    var itemCarbs: Double = 0.0,
    @ColumnInfo(name = "fats")
    var itemFats: Double = 0.0,
    @ColumnInfo(name = "category")
    var itemCategory: String = "Dairy products",
    @ColumnInfo(name = "quantity")
    var itemQuantity: Double = 0.0
)