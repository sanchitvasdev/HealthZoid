package com.sanchit.healthzoid.foods

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.database.MealItemDatabase
import com.sanchit.healthzoid.databinding.FragmentFoodsBinding
import com.sanchit.healthzoid.domain.MealItemModel
import com.sanchit.healthzoid.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodsFragment : Fragment() {
    private lateinit var binding: FragmentFoodsBinding
    private lateinit var database: MealItemDatabase
    private lateinit var adapter: FoodsAdapter
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_foods, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = FoodsFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = FoodsViewModelFactory(arguments.timeOfFood, application)
        val foodsViewModel =
            ViewModelProvider(this, viewModelFactory).get(FoodsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = foodsViewModel

        adapter = FoodsAdapter(MealItemclickListener {
                foodTitle -> foodsViewModel.onMealItemClicked(foodTitle)
        })
        binding.foodsRecyclerView.adapter = adapter

        foodsViewModel.mealItems.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        database = MealItemDatabase.getInstance(application)

        (activity as AppCompatActivity).supportActionBar?.title = "Food Items"

        foodsViewModel.navigateToDailyDietFragment.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(FoodsFragmentDirections.actionFoodsFragmentToDailyDietFragment())
                foodsViewModel.onNavigated()
            }
        })

        foodsViewModel.breakfastmealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        it.forEach {
                            database.mealItemDao.insertBreakfastMealItem(it.asBreakfastMealItem())
                        }
                    }
                }
                binding.mealItemsCountButton.text = resources.getString(R.string.food_items_added,it.size)
            }
        })

        foodsViewModel.lunchmealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        it.forEach {
                            database.mealItemDao.insertLunchMealItem(it.asLunchMealItem())
                        }
                    }
                }
                binding.mealItemsCountButton.text = resources.getString(R.string.food_items_added,it.size)
            }
        })

        foodsViewModel.snacksmealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        it.forEach {
                            database.mealItemDao.insertSnacksMealItem(it.asSnacksMealItem())
                        }
                    }
                }
                binding.mealItemsCountButton.text = resources.getString(R.string.food_items_added,it.size)
            }
        })

        foodsViewModel.dinnermealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        it.forEach {
                            database.mealItemDao.insertDinnerMealItem(it.asDinnerMealItem())
                        }
                    }
                }
                binding.mealItemsCountButton.text = resources.getString(R.string.food_items_added,it.size)
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    displayMealItem(newText)
                }
                return true
            }
        })
        binding.searchView.setOnCloseListener {
            adapter.submitList(null)
            database.mealItemDao.getMealItems().observe(viewLifecycleOwner, Observer {
                val list = arrayListOf<MealItemModel>()
                list.addAll(
                    it.asDomainModel()
                )
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
            })
            false
        }

        return binding.root
    }

    fun displayMealItem(newText: String = "") {
        database.mealItemDao.getItemWithTypedString().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                adapter.submitList(null)
                val list = arrayListOf<MealItemModel>()
                list.addAll(
                    it.filter { mealItem ->
                        mealItem.itemTitle.contains(newText,true)
                    }.asDomainModel()
                )
                adapter.submitList(list)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(context,"No item found",Toast.LENGTH_SHORT).show()
            }
        })
    }
}