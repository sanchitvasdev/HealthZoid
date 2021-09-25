package com.sanchit.healthzoid.foods

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanchit.healthzoid.databinding.MealitemBinding
import com.sanchit.healthzoid.domain.MealItemModel


class FoodsAdapter(val clickListener: MealItemclickListener) : ListAdapter<MealItemModel, FoodsAdapter.ViewHolder>(MealItemDiffCallBack()) {
    class ViewHolder private constructor(val binding: MealitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealItemModel, clickListener: MealItemclickListener) {
            binding.food = item
            binding.addmealItem.setOnClickListener {
                clickListener.onClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealitemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class MealItemDiffCallBack : DiffUtil.ItemCallback<MealItemModel>() {
    override fun areItemsTheSame(oldItem: MealItemModel, newItem: MealItemModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MealItemModel, newItem: MealItemModel): Boolean {
        return oldItem.itemTitle == newItem.itemTitle
    }
}

class MealItemclickListener(val clickListener: (foodTitle: String) -> Unit) {
    fun onClick(mealItem: MealItemModel) = clickListener(mealItem.itemTitle)
}

