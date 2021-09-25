package com.sanchit.healthzoid.dailydiet

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sanchit.healthzoid.CommonViewModel
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.database.MealItemDatabase
import com.sanchit.healthzoid.databinding.FragmentDailyDietBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class DailyDietFragment : Fragment() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDailyDietBinding= DataBindingUtil.inflate(
                inflater,R.layout.fragment_daily_diet,container,false)

        val application = requireNotNull(this.activity).application
        val breakfastAdapter = BreakfastAdapter()
        val lunchAdapter = LunchAdapter()
        val snacksAdapter = SnacksAdapter()
        val dinnerAdapter = DinnerAdapter()
        val database = MealItemDatabase.getInstance(application)
        val activityViewModel: CommonViewModel by activityViewModels()

        (activity as AppCompatActivity).supportActionBar?.title = "Your Diet"
        val viewModelFactory = DailyDietViewModelFactory(application)
        val dailyDietViewModel = ViewModelProvider(this,viewModelFactory).get(DailyDietViewModel::class.java)

        binding.dailyDietViewModel = dailyDietViewModel
        binding.lifecycleOwner = this

        binding.breakfastTextView.setOnTouchListener(OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.breakfastTextView.right-binding.breakfastTextView.totalPaddingRight) {
                    view.findNavController().navigate(DailyDietFragmentDirections.actionDailyDietFragmentToFoodsFragment(0))
                    return@OnTouchListener true
                }
            }
            true
        })

        binding.lunchTextView.setOnTouchListener(OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.lunchTextView.right-binding.lunchTextView.totalPaddingRight) {
                    view.findNavController().navigate(DailyDietFragmentDirections.actionDailyDietFragmentToFoodsFragment(1))
                    return@OnTouchListener true
                }
            }
            true
        })

        binding.snacksTextView.setOnTouchListener(OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.snacksTextView.right-binding.snacksTextView.totalPaddingRight) {
                    view.findNavController().navigate(DailyDietFragmentDirections.actionDailyDietFragmentToFoodsFragment(2))
                    return@OnTouchListener true
                }
            }
            true
        })

        binding.dinnerTextView.setOnTouchListener(OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.dinnerTextView.right-binding.dinnerTextView.totalPaddingRight) {
                    view.findNavController().navigate(DailyDietFragmentDirections.actionDailyDietFragmentToFoodsFragment(3))
                    return@OnTouchListener true
                }
            }
            true
        })

        dailyDietViewModel.visibility.observe(viewLifecycleOwner, Observer {
            when(it){
                true -> {
                    binding.apply {
                        proteinTextView.visibility = View.GONE
                        proteinValueTextView.visibility = View.GONE
                        proteinProgressBar.visibility = View.GONE
                        carbsTextView.visibility = View.GONE
                        carbsValueTextView.visibility = View.GONE
                        carbsProgressBar.visibility = View.GONE
                        fatsTextView.visibility = View.GONE
                        fatsValueTextView.visibility = View.GONE
                        fatsProgressBar.visibility = View.GONE
                        binding.proteinExceededTextView.visibility = View.GONE
                        binding.carbsExceededTextView.visibility = View.GONE
                        binding.fatsExceededTextView.visibility = View.GONE
                        slideImageView.setImageResource(R.drawable.ic_down_arrow)
                    }
                }
                false -> {
                    binding.apply {
                        proteinTextView.visibility = View.VISIBLE
                        proteinValueTextView.visibility = View.VISIBLE
                        proteinProgressBar.visibility = View.VISIBLE
                        carbsTextView.visibility = View.VISIBLE
                        carbsValueTextView.visibility = View.VISIBLE
                        carbsProgressBar.visibility = View.VISIBLE
                        fatsTextView.visibility = View.VISIBLE
                        fatsValueTextView.visibility = View.VISIBLE
                        fatsProgressBar.visibility = View.VISIBLE
                        slideImageView.setImageResource(R.drawable.ic_up_arrow)
                    }
                    dailyDietViewModel.proteinLimit.observe(viewLifecycleOwner, Observer {
                        if(it){
                            binding.proteinExceededTextView.visibility = View.VISIBLE
                        }else{
                            binding.proteinExceededTextView.visibility = View.GONE
                        }
                    })
                   
                    dailyDietViewModel.carbsLimit.observe(viewLifecycleOwner, Observer {
                        if(it){
                            binding.carbsExceededTextView.visibility = View.VISIBLE
                        }else{
                            binding.carbsExceededTextView.visibility = View.GONE
                        }
                    })

                    dailyDietViewModel.fatsLimit.observe(viewLifecycleOwner, Observer {
                        if(it){
                            binding.fatsExceededTextView.visibility = View.VISIBLE
                        }else{
                            binding.fatsExceededTextView.visibility = View.GONE
                        }
                    })
                }
            }
        })

        binding.breakfastRecyclerView.adapter = breakfastAdapter
        binding.lunchRecyclerView.adapter = lunchAdapter
        binding.snacksRecyclerView.adapter = snacksAdapter
        binding.dinnerRecyclerView.adapter = dinnerAdapter

        val sharedPreferences = application.applicationContext.getSharedPreferences("lastOpenedDate",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val calender = Calendar.getInstance()
        val currentdate = DateFormat.getDateInstance().format(calender.time)
        val currenttime = DateFormat.getTimeInstance().format(calender.time)

        if(currenttime.substring(0,8)=="12:00:00"&&currenttime.substring(9)=="am"){
            Toast.makeText(context,"Changes have been done to the app. Please restart",Toast.LENGTH_LONG).show()
        }
        if(currentdate != sharedPreferences.getString("PreviousDate",null)){
            if(sharedPreferences.getString("PreviousDate",null)!=null){
               dailyDietViewModel.clearAll(application)
                activityViewModel.clearRecyclerViews()
            }
        }
        editor.putString("PreviousDate", currentdate)
        editor.apply()

        dailyDietViewModel.reset()
        activityViewModel.updateRecyclerViews()

        activityViewModel.breakfastmealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                breakfastAdapter.submitList(null)
                breakfastAdapter.notifyDataSetChanged()
                breakfastAdapter.submitList(it.toList())
                breakfastAdapter.notifyDataSetChanged()
                for (item in it){
                    dailyDietViewModel.update(item.itemCalories,item.itemProtein,item.itemCarbs,item.itemFats)
                }
            }
        })

        activityViewModel.lunchmealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                lunchAdapter.submitList(null)
                lunchAdapter.notifyDataSetChanged()
                lunchAdapter.submitList(it.toList())
                lunchAdapter.notifyDataSetChanged()
                for (item in it){
                    dailyDietViewModel.update(item.itemCalories,item.itemProtein,item.itemCarbs,item.itemFats)
                }
            }
        })

        activityViewModel.snacksmealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                snacksAdapter.submitList(null)
                snacksAdapter.notifyDataSetChanged()
                snacksAdapter.submitList(it.toList())
                snacksAdapter.notifyDataSetChanged()
                for (item in it){
                    dailyDietViewModel.update(item.itemCalories,item.itemProtein,item.itemCarbs,item.itemFats)
                }
            }
        })

        activityViewModel.dinnermealItemsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                dinnerAdapter.submitList(null)
                dinnerAdapter.notifyDataSetChanged()
                dinnerAdapter.submitList(it.toList())
                dinnerAdapter.notifyDataSetChanged()
                for (item in it){
                    dailyDietViewModel.update(item.itemCalories,item.itemProtein,item.itemCarbs,item.itemFats)
                }
            }
        })

        dailyDietViewModel.caloriesLimit.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.caloriesExceededTextView.visibility = View.VISIBLE
            }else{
                binding.caloriesExceededTextView.visibility = View.GONE
            }
        })
        
        initSwipeBreakfast(binding,activityViewModel,breakfastAdapter,database,dailyDietViewModel)
        initSwipeLunch(binding,activityViewModel,lunchAdapter, database,dailyDietViewModel)
        initSwipeSnacks(binding,activityViewModel,snacksAdapter,database,dailyDietViewModel)
        initSwipeDinner(binding,activityViewModel,dinnerAdapter, database,dailyDietViewModel)
        return binding.root
    }
    fun initSwipeBreakfast(binding: FragmentDailyDietBinding,activityViewModel: CommonViewModel, breakfastAdapter: BreakfastAdapter,database: MealItemDatabase,viewModel: DailyDietViewModel) {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        database.mealItemDao.clearBreakfastMealItem(breakfastAdapter.getItem(position).itemTitle)
                    }
                    viewModel.reset()
                    activityViewModel.clearRecyclerViews()
                    activityViewModel.updateRecyclerViews()
                    breakfastAdapter.notifyDataSetChanged()
                }
            }
            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    val icon: Bitmap
                    if(dX < 0){
                        icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_delete_white,null)!!.toBitmap()
                        paint.color = Color.parseColor("#D32F2F")
                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )
                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.breakfastRecyclerView)
    }
    fun initSwipeLunch(binding: FragmentDailyDietBinding,activityViewModel: CommonViewModel,lunchAdapter: LunchAdapter,database: MealItemDatabase,viewModel: DailyDietViewModel) {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        database.mealItemDao.clearLunchMealItem(lunchAdapter.getItem(position).itemTitle)
                    }
                    viewModel.reset()
                    activityViewModel.clearRecyclerViews()
                    activityViewModel.updateRecyclerViews()
                    lunchAdapter.notifyDataSetChanged()
                }
            }
            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    val icon: Bitmap
                    if(dX < 0){
                        icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_delete_white,null)!!.toBitmap()
                        paint.color = Color.parseColor("#D32F2F")
                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )
                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.lunchRecyclerView)
    }
    fun initSwipeSnacks(binding: FragmentDailyDietBinding,activityViewModel: CommonViewModel,snacksAdapter: SnacksAdapter,database: MealItemDatabase,viewModel: DailyDietViewModel) {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        database.mealItemDao.clearSnacksMealItem(snacksAdapter.getItem(position).itemTitle)
                    }
                    viewModel.reset()
                    activityViewModel.clearRecyclerViews()
                    activityViewModel.updateRecyclerViews()
                    snacksAdapter.notifyDataSetChanged()
                }
            }
            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    val icon: Bitmap
                    if(dX < 0){
                        icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_delete_white,null)!!.toBitmap()
                        paint.color = Color.parseColor("#D32F2F")
                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )
                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.snacksRecyclerView)
    }
    fun initSwipeDinner(binding: FragmentDailyDietBinding,activityViewModel: CommonViewModel,dinnerAdapter: DinnerAdapter,database: MealItemDatabase,viewModel: DailyDietViewModel) {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        database.mealItemDao.clearDinnerMealItem(dinnerAdapter.getItem(position).itemTitle)
                    }
                    viewModel.reset()
                    activityViewModel.clearRecyclerViews()
                    activityViewModel.updateRecyclerViews()
                    dinnerAdapter.notifyDataSetChanged()
                }
            }
            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    val icon: Bitmap
                    if(dX < 0){
                        icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_delete_white,null)!!.toBitmap()
                        paint.color = Color.parseColor("#D32F2F")
                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )
                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.dinnerRecyclerView)
    }
}
