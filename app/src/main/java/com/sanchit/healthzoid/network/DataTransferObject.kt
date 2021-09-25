package com.sanchit.healthzoid.network

import android.graphics.Color
import com.sanchit.healthzoid.database.*
import com.sanchit.healthzoid.domain.MealItemModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkMealItemContainer(val mealItems: List<NetworkMealItem>)

@JsonClass(generateAdapter = true)
data class NetworkMealItem(
        val Food: String,
        val Measure: String,
        val Grams: Double,
        val Calories: Double,
        val Protein: Double,
        val Fat: Double,
        val SatFat: Double,
        val Fiber: Double,
        val Carbs: Double,
        val Category: String
)

fun List<MealItem>.asDomainModel(): List<MealItemModel>{
    return map {
        MealItemModel(
                itemTitle = it.itemTitle,
                itemColor = it.itemColor,
                itemCalories = it.itemCalories,
                itemProtein = it.itemProtein,
                itemCarbs = it.itemCarbs,
                itemFats = it.itemFats,
                itemCategory = it.itemCategory,
                itemQuantity = it.itemQuantity
        )
    }
}

fun NetworkMealItemContainer.asDatabaseModel(): Array<MealItem>{
    return mealItems.map {
        MealItem(
            itemTitle = it.Food,
            itemColor = Color.GREEN,
            itemCalories = it.Calories,
            itemProtein = it.Protein,
            itemCarbs = it.Carbs,
            itemFats = it.Fat,
            itemCategory = it.Category,
            itemQuantity = it.Grams
        )
    }.toTypedArray()
}

fun MealItem.asBreakfastMealItem(): BreakfastMealItem{
    return BreakfastMealItem(
        itemTitle = itemTitle,
        itemColor = itemColor,
        itemCalories = itemCalories,
        itemProtein = itemProtein,
        itemCarbs = itemCarbs,
        itemFats = itemFats,
        itemCategory = itemCategory,
        itemQuantity = itemQuantity,
        likedOrNot = false
    )
}

fun MealItem.asLunchMealItem(): LunchMealItem{
    return LunchMealItem(
        itemTitle = itemTitle,
        itemColor = itemColor,
        itemCalories = itemCalories,
        itemProtein = itemProtein,
        itemCarbs = itemCarbs,
        itemFats = itemFats,
        itemCategory = itemCategory,
        itemQuantity = itemQuantity
    )
}

fun MealItem.asSnacksMealItem(): SnacksMealItem{
    return SnacksMealItem(
            itemTitle = itemTitle,
            itemColor = itemColor,
            itemCalories = itemCalories,
            itemProtein = itemProtein,
            itemCarbs = itemCarbs,
            itemFats = itemFats,
            itemCategory = itemCategory,
            itemQuantity = itemQuantity
        )
}

fun MealItem.asDinnerMealItem(): DinnerMealItem{
    return DinnerMealItem(
            itemTitle = itemTitle,
            itemColor = itemColor,
            itemCalories = itemCalories,
            itemProtein = itemProtein,
            itemCarbs = itemCarbs,
            itemFats = itemFats,
            itemCategory = itemCategory,
            itemQuantity = itemQuantity
        )
}

fun MealItemModel.asBreakfastMealItem(): BreakfastMealItem{
    return BreakfastMealItem(
        itemTitle = itemTitle,
        itemColor = itemColor,
        itemCalories = itemCalories,
        itemProtein = itemProtein,
        itemCarbs = itemCarbs,
        itemFats = itemFats,
        itemCategory = itemCategory,
        itemQuantity = itemQuantity,
        likedOrNot = false
    )
}

fun MealItemModel.asLunchMealItem(): LunchMealItem{
    return LunchMealItem(
        itemTitle = itemTitle,
        itemColor = itemColor,
        itemCalories = itemCalories,
        itemProtein = itemProtein,
        itemCarbs = itemCarbs,
        itemFats = itemFats,
        itemCategory = itemCategory,
        itemQuantity = itemQuantity
    )
}

fun MealItemModel.asSnacksMealItem(): SnacksMealItem{
    return SnacksMealItem(
        itemTitle = itemTitle,
        itemColor = itemColor,
        itemCalories = itemCalories,
        itemProtein = itemProtein,
        itemCarbs = itemCarbs,
        itemFats = itemFats,
        itemCategory = itemCategory,
        itemQuantity = itemQuantity
    )
}

fun MealItemModel.asDinnerMealItem(): DinnerMealItem{
    return DinnerMealItem(
        itemTitle = itemTitle,
        itemColor = itemColor,
        itemCalories = itemCalories,
        itemProtein = itemProtein,
        itemCarbs = itemCarbs,
        itemFats = itemFats,
        itemCategory = itemCategory,
        itemQuantity = itemQuantity
    )
}
