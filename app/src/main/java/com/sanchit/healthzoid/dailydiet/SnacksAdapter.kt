package com.sanchit.healthzoid.dailydiet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.database.BreakfastMealItem
import com.sanchit.healthzoid.database.SnacksMealItem
import com.sanchit.healthzoid.databinding.SnacksmealitemBinding

class SnacksAdapter(): ListAdapter<SnacksMealItem, SnacksAdapter.ViewHolder>(MealItemDiffCallBack()){
    class ViewHolder private constructor(val binding: SnacksmealitemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SnacksMealItem){
            binding.food = item
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SnacksmealitemBinding.inflate(layoutInflater,parent,false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    public override fun getItem(position: Int): SnacksMealItem {
        return currentList[position]
    }

    class MealItemDiffCallBack: DiffUtil.ItemCallback<SnacksMealItem>(){
        override fun areItemsTheSame(oldItem: SnacksMealItem, newItem: SnacksMealItem): Boolean {
            return oldItem.itemTitle == newItem.itemTitle
        }

        override fun areContentsTheSame(oldItem: SnacksMealItem, newItem: SnacksMealItem): Boolean {
            return oldItem == newItem
        }
    }
}

@BindingAdapter("snacksfoodImage")
fun ShapeableImageView.setsnacksFoodImage(item: SnacksMealItem){
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

@BindingAdapter("snacksfoodTitle")
fun MaterialTextView.setsnacksFoodTitle(item: SnacksMealItem?){
    item?.let {
        text = item.itemTitle
    }
}

@BindingAdapter("snacksfoodQuantity")
fun MaterialTextView.setsnacksFoodQuantity(item: SnacksMealItem?){
    item?.let {
        text = resources.getString(R.string.grams_text,item.itemQuantity)
    }
}

@BindingAdapter("snacksfoodCalories")
fun MaterialTextView.setsnacksFoodCalories(item: SnacksMealItem?){
    item?.let {
        text = resources.getString(R.string.kcals_text,item.itemCalories)
    }
}

@BindingAdapter("snacksfoodProtein")
fun MaterialTextView.setsnacksFoodProtein(item: SnacksMealItem?){
    item?.let {
        text = resources.getString(R.string.proteinconvert,item.itemProtein)
    }
}

@BindingAdapter("snacksfoodCarbs")
fun MaterialTextView.setsnacksFoodCarbs(item: SnacksMealItem?){
    item?.let {
        text = resources.getString(R.string.carbsconvert,item.itemCarbs)
    }
}

@BindingAdapter("snacksfoodFats")
fun MaterialTextView.setsnacksFoodFats(item: SnacksMealItem?){
    item?.let {
        text = resources.getString(R.string.fatsconvert,item.itemFats)
    }
}
