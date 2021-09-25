package com.sanchit.healthzoid.foods

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.domain.MealItemModel

@BindingAdapter("foodImage")
fun ShapeableImageView.setFoodImage(item: MealItemModel){
    setImageResource(when(item.itemCategory){
        "Dairy products" -> R.mipmap.ic_dairy_products_round
        "Fats Oils Shortenings" -> R.mipmap.ic_fatsoilsshortenings_round
        "Meat Poultry" -> R.mipmap.ic_meatpoultry_round
        "Fish Seafood" -> R.mipmap.ic_fishseafoods_round
        "Vegetables AE" -> R.mipmap.ic_vegetablesae_round
        "Vegetables FP" -> R.mipmap.ic_vegetablesfp_round
        "Vegetables RZ" -> R.mipmap.ic_vegetablesrz_round
        "Fruits AF" -> R.mipmap.ic_fruits_round
        "Fruits GP" -> R.mipmap.ic_fruits_round
        "Fruits RZ" -> R.mipmap.ic_fruits_round
        "Breads cereals fastfoodgrains" -> R.mipmap.ic_breadandcereals_round
        "Soups" -> R.mipmap.ic_soups_round
        "Desserts sweets" -> R.mipmap.ic_dessertsweets_round
        "Jams Jellies" -> R.mipmap.ic_jamsandjellies_round
        "Seeds and Nuts" -> R.mipmap.ic_seedsandnuts_round
        "DrinksAlcohol Beverages" -> R.mipmap.ic_alcoholbeverages_round
        else -> R.mipmap.ic_dairy_products_round
    })
}

@BindingAdapter("foodTitle")
fun MaterialTextView.setFoodTitle(item: MealItemModel?){
    item?.let {
        text = item.itemTitle
    }
}

@BindingAdapter("foodQuantity")
fun MaterialTextView.setFoodQuantity(item: MealItemModel?){
    item?.let {
        text = resources.getString(R.string.grams_text,item.itemQuantity)
    }
}

@BindingAdapter("foodCalories")
fun MaterialTextView.setFoodCalories(item: MealItemModel?){
    item?.let {
        text = resources.getString(R.string.kcals_text,item.itemCalories)
    }
}

@BindingAdapter("foodProtein")
fun MaterialTextView.setFoodProtein(item: MealItemModel?){
    item?.let {
        text = resources.getString(R.string.proteinconvert,item.itemProtein)
    }
}

@BindingAdapter("foodCarbs")
fun MaterialTextView.setFoodCarbs(item: MealItemModel?){
    item?.let {
        text = resources.getString(R.string.carbsconvert,item.itemCarbs)
    }
}

@BindingAdapter("foodFats")
fun MaterialTextView.setFoodFats(item: MealItemModel?){
    item?.let {
        text = resources.getString(R.string.fatsconvert,item.itemFats)
    }
}

//@BindingAdapter("setClickListener")
//fun ShapeableImageView.setOnClickListener(item: MealItemModel){
//    setOnClickListener {
//        if(it.drawableState.equals(R.drawable.ic_add_button)){
//            setImageResource(R.drawable.ic_add_button_filled)
//        }else{
//            setImageResource(R.drawable.ic_add_button)
//        }
//    }
//}


