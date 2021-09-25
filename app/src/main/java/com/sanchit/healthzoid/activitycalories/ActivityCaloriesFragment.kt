package com.sanchit.healthzoid.activitycalories

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.customDrawables.TextViewDrawable
import com.sanchit.healthzoid.databinding.FragmentActivityCaloriesBinding

class ActivityCaloriesFragment : Fragment() {
    private lateinit var binding: FragmentActivityCaloriesBinding
    private lateinit var viewModelFactory: ActivityCaloriesViewModelFactory
    private val viewModel: ActivityCaloriesViewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(ActivityCaloriesViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_activity_calories, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Activity Calories"

        val application = requireNotNull(this.activity).application
        viewModelFactory = ActivityCaloriesViewModelFactory(application)
        val sharedPreferences = application.applicationContext.getSharedPreferences("activityDetails",Context.MODE_PRIVATE)

        val activitySpinnerValues = arrayOf("Sitting", "Sitting playing cards", "Standing",
            "Strolling at a slow pace", "Washing dishes", "Hatha yoga", "Fishing while sitting",
            "Household chores","Weight training (lighter weights)","Weight training (heavier weights)",
            "Playing golf","Walking (3.5-4 mph)","Walking (4.5 mph)",  "Running at 7 mph", "Yard work",
            "Swimming at moderate pace", "Bicycling at 12-14 mph","Circuit training","Singles tennis",
            "Shoveling, digging", "Competitive soccer")
        val activitySpinnerList = arrayListOf<String>()
        activitySpinnerList.addAll(activitySpinnerValues)
        val activitySpinnerAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item,activitySpinnerList)
        activitySpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item)
        binding.activitySpinner.adapter = activitySpinnerAdapter

        val spinnerPosition = activitySpinnerAdapter.getPosition(sharedPreferences.getString("activity","Sitting"))
        binding.activitySpinner.setSelection(spinnerPosition)

        binding.bodyweightEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"kg"),null)
        binding.timeInMinutesEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"min"),null)

        binding.bodyweightEditText.setText(sharedPreferences.getFloat("bodyweight",0f).toString())
        binding.timeInMinutesEditText.setText(sharedPreferences.getInt("timeInMinutes",0).toString())
        binding.activityCaloriesTextView.text = resources.getString(R.string.your_activity_calories_are,sharedPreferences.getFloat("activityCaloriesValue",0f))

        binding.calculateButton5.setOnClickListener {
            var activityCalories = 0f
            when(binding.activitySpinner.selectedItem.toString()){
                "Sitting"  -> activityCalories = (1.3*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Sitting playing cards" -> activityCalories = (1.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Standing" -> activityCalories = (1.8*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Strolling at a slow pace" -> activityCalories = (2.0*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Washing dishes" -> activityCalories = (2.2*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Hatha yoga" -> activityCalories = (2.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Fishing while sitting" -> activityCalories = (2.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Household chores" -> activityCalories = (3.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Weight training (lighter weights)" -> activityCalories = (3.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Weight training (heavier weights)" -> activityCalories = (5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Playing golf" -> activityCalories = (4.3*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Walking (3.5-4 mph)" -> activityCalories = (5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Walking (4.5 mph)" -> activityCalories = (6.3*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Running at 7 mph" -> activityCalories = (11.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Yard work" -> activityCalories = (5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Swimming at moderate pace" -> activityCalories = (6*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Bicycling at 12-14 mph" -> activityCalories = (8*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Circuit training" -> activityCalories = (8*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Singles tennis" -> activityCalories = (8*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Shoveling, digging" -> activityCalories = (8.5*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
                "Competitive soccer" -> activityCalories = (10*binding.bodyweightEditText.text.toString().toFloat()*binding.timeInMinutesEditText.text.toString().toFloat()*(3.5/200)).toFloat()
            }
            viewModel.store(activityCalories)
            binding.activityCaloriesTextView.text = resources.getString(R.string.your_activity_calories_are,activityCalories)
        }
        
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("activityDetails",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("activity",binding.activitySpinner.selectedItem.toString())
        editor.putFloat("bodyweight",binding.bodyweightEditText.text.toString().toFloat())
        editor.putInt("timeInMinutes",binding.timeInMinutesEditText.text.toString().toInt())
        editor.putFloat("activityCaloriesValue",viewModel.activityCaloriesValue.value!!)
        editor.apply()
    }
}