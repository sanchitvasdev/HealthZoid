package com.sanchit.healthzoid.domain

import com.sanchit.healthzoid.R

data class MealItemModel(
        var itemTitle: String = "Not titled",
        var itemColor: Int = R.color.purple,
        var itemCalories: Double = 0.0,
        var itemProtein: Double = 0.0,
        var itemCarbs: Double = 0.0,
        var itemFats: Double = 0.0,
        var itemCategory: String = "Dairy products",
        var itemQuantity: Double = 0.0
)





