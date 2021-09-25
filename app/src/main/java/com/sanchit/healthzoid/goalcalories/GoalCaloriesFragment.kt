package com.sanchit.healthzoid.goalcalories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.customDrawables.TextViewDrawable
import com.sanchit.healthzoid.dailydiet.DailyDietFragmentDirections
import com.sanchit.healthzoid.databinding.FragmentGoalCaloriesBinding

class GoalCaloriesFragment : Fragment() {
    private lateinit var binding: FragmentGoalCaloriesBinding
    private lateinit var viewModelFactory: GoalCaloriesViewModelFactory
    private val viewModel by lazy{
        ViewModelProvider(this,viewModelFactory).get(GoalCaloriesViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_goal_calories, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Goal Calories"

        val application = requireNotNull(this.activity).application
        viewModelFactory = GoalCaloriesViewModelFactory(application)

        binding.currentBodyWeightEditText.setCompoundDrawables(null,null, TextViewDrawable(resources,"kg"),null)
        binding.targetBodyWeightEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"kg"),null)

        binding.teeEditText.setOnTouchListener(View.OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.teeEditText.right - binding.teeEditText.totalPaddingRight) {
                    view.findNavController().navigate(GoalCaloriesFragmentDirections.actionGoalCaloriesFragmentToYourBmrAndTeeFragment())
                    return@OnTouchListener true
                }
            }
            false
        })

        val sharedPreferences = application.applicationContext.getSharedPreferences("goalCalories",Context.MODE_PRIVATE)
        binding.currentBodyWeightEditText.setText(sharedPreferences.getFloat("currentWeight",0f).toString())
        binding.targetBodyWeightEditText.setText(sharedPreferences.getFloat("targetWeight",0f).toString())
        binding.targetWeeksEditText.setText(sharedPreferences.getInt("targetWeeks",0).toString())
        binding.teeEditText.setText(sharedPreferences.getFloat("teeValue",0f).toString())
        binding.goalCaloriesTextView.text = resources.getString(R.string.your_goal_calories_are,sharedPreferences.getFloat("goalCaloriesValue",0f))

        binding.calculateButton2.setOnClickListener {
            var caloriesrequired: Float
            if(binding.currentBodyWeightEditText.text.toString().toFloat()>=binding.targetBodyWeightEditText.text.toString().toFloat()){
                caloriesrequired = ((binding.currentBodyWeightEditText.text.toString().toFloat()
                        -binding.targetBodyWeightEditText.text.toString().toFloat())
                        /binding.targetWeeksEditText.text.toString().toInt())*(1100)
                if(caloriesrequired==0f){
                    caloriesrequired = binding.teeEditText.text.toString().toFloat()
                }else{
                    caloriesrequired -= binding.teeEditText.text.toString().toFloat()
                }
            }else{
                caloriesrequired = ((binding.targetBodyWeightEditText.text.toString().toFloat()
                        -binding.currentBodyWeightEditText.text.toString().toFloat())
                        /binding.targetWeeksEditText.text.toString().toInt())*(1100)
                caloriesrequired += binding.teeEditText.text.toString().toFloat()
            }
            if(binding.teeEditText.text.toString().toFloat()==0f){
                Toast.makeText(context,"You haven't entered your tee",Toast.LENGTH_SHORT).show()
            }else if(caloriesrequired<1200||caloriesrequired>3200){
                Toast.makeText(context,"Your goal is too aggressive. Please add more number of weeks",Toast.LENGTH_LONG).show()
            }else{
                binding.goalCaloriesTextView.text = resources.getString(R.string.your_goal_calories_are,caloriesrequired)
                viewModel.store(caloriesrequired)
            }
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("goalCalories",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("currentWeight",binding.currentBodyWeightEditText.text.toString().toFloat())
        editor.putFloat("targetWeight",binding.targetBodyWeightEditText.text.toString().toFloat())
        editor.putInt("targetWeeks",binding.targetWeeksEditText.text.toString().toInt())
        editor.putFloat("teeValue",binding.teeEditText.text.toString().toFloat())
        editor.putFloat("goalCaloriesValue",viewModel.goalCaloriesValue.value!!)
        editor.apply()
    }
}