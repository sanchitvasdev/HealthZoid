package com.sanchit.healthzoid.yourbmrandtee

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
import com.sanchit.healthzoid.customDrawables.TextViewDrawable
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.databinding.FragmentYourBmrAndTeeBinding

class YourBmrAndTeeFragment : Fragment() {
    private lateinit var binding: FragmentYourBmrAndTeeBinding
    private lateinit var viewModelFactory: YourBmrAndTeeViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(YourBmrAndTeeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_your_bmr_and_tee, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Your BMR and TEE"
        val application = requireNotNull(this.activity).application
        viewModelFactory = YourBmrAndTeeViewModelFactory(application)
        val sharedPreferences = application.applicationContext.getSharedPreferences("bmrAndTeeDetails",Context.MODE_PRIVATE)

        val activityCountSpinnerList = arrayListOf<String>()
        val genderSpinnerList = arrayListOf<String>()

        val activityCountSpinnerValues = arrayOf("Sedentary: little or no exercise",
            "Light: exercise 1-3 times/week",
            "Moderate: exercise 3-5 times/week",
            "Active: exercise 6-7 times/week",
            "Very Active: exercise daily")
        val genderSpinnerValues = arrayOf("Male", "Female")

        activityCountSpinnerList.addAll(activityCountSpinnerValues)
        genderSpinnerList.addAll(genderSpinnerValues)

        val activityCountSpinnerAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item,activityCountSpinnerList)
        activityCountSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item)

        val genderSpinnerAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item,genderSpinnerList)
        genderSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item)

        binding.activityCountSpinner.adapter = activityCountSpinnerAdapter
        binding.genderSpinner2.adapter = genderSpinnerAdapter
        binding.activityCountSpinner.prompt = "Lifestyle"
        binding.genderSpinner2.prompt = "Gender"

        val activityCountSpinnerPosition = activityCountSpinnerAdapter.getPosition(sharedPreferences.getString("activityCountBmr","Sedentary: little or no exercise"))
        val genderSpinnerPosition = genderSpinnerAdapter.getPosition(sharedPreferences.getString("genderBmr","Male"))

        binding.activityCountSpinner.setSelection(activityCountSpinnerPosition)
        binding.genderSpinner2.setSelection(genderSpinnerPosition)
        binding.ageEditText.setText(sharedPreferences.getInt("age",0).toString())
        binding.heightEditText.setText(sharedPreferences.getFloat("heightBmr",0f).toString())
        binding.weightEditText.setText(sharedPreferences.getFloat("weightBmr",0f).toString())
        binding.bmrAndTeeTextView.text = resources.getString(R.string.your_bmr_and_tee_are,sharedPreferences.getFloat("bmr",0f),sharedPreferences.getFloat("tee",0f))

        binding.heightEditText.setCompoundDrawables(null,null, TextViewDrawable(resources, "cm"),null)
        binding.weightEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"kg"),null)

        binding.calculateButton3.setOnClickListener {
            var bmr = 0f
            var tee = 0f
            if(binding.genderSpinner2.selectedItem.toString()=="Male"){
                bmr = (13.397*binding.weightEditText.text.toString().toFloat() +
                        binding.heightEditText.text.toString().toFloat()*4.799 -
                        binding.ageEditText.text.toString().toFloat()*5.677 + 88.362).toFloat()
                when(binding.activityCountSpinner.selectedItem.toString()){
                    "Sedentary: little or no exercise" -> tee = bmr*1.2.toFloat()
                    "Light: exercise 1-3 times/week" -> tee = bmr*1.375.toFloat()
                    "Moderate: exercise 3-5 times/week" -> tee = bmr*1.55.toFloat()
                    "Active: exercise 6-7 times/week" -> tee = bmr*1.725.toFloat()
                    "Very Active: exercise daily" -> tee = bmr*1.9.toFloat()
                }
            }else{
                bmr = (9.247*binding.weightEditText.text.toString().toFloat() +
                        binding.heightEditText.text.toString().toFloat()*3.098 -
                        binding.ageEditText.text.toString().toFloat()*4.33 + 447.593).toFloat()
                when(binding.activityCountSpinner.selectedItem.toString()){
                    "Sedentary: little or no exercise" -> tee = bmr*1.2.toFloat()
                    "Light: exercise 1-3 times/week" -> tee = bmr*1.375.toFloat()
                    "Moderate: exercise 3-5 times/week" -> tee = bmr*1.55.toFloat()
                    "Active: exercise 6-7 times/week" -> tee = bmr*1.725.toFloat()
                    "Very Active: exercise daily" -> tee = bmr*1.9.toFloat()
                }
            }
            viewModel.store(bmr,tee)
            binding.bmrAndTeeTextView.text = resources.getString(R.string.your_bmr_and_tee_are,bmr,tee)
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("bmrAndTeeDetails",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("activityCountBmr",binding.activityCountSpinner.selectedItem.toString())
        editor.putString("genderBmr",binding.genderSpinner2.selectedItem.toString())
        editor.putInt("age",binding.ageEditText.text.toString().toInt())
        editor.putFloat("heightBmr",binding.heightEditText.text.toString().toFloat())
        editor.putFloat("weightBmr",binding.weightEditText.text.toString().toFloat())
        editor.putFloat("bmr",viewModel.bmrValue.value!!)
        editor.putFloat("tee",viewModel.teeValue.value!!)
        editor.apply()
    }
}