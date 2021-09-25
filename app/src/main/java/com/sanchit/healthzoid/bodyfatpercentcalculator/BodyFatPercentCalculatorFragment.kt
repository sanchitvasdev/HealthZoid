package com.sanchit.healthzoid.bodyfatpercentcalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.customDrawables.TextViewDrawable
import com.sanchit.healthzoid.databinding.FragmentBodyFatPercentCalculatorBinding
import kotlin.math.log10

class BodyFatPercentCalculatorFragment : Fragment(){
    private lateinit var binding: FragmentBodyFatPercentCalculatorBinding
    private lateinit var viewModelFactory: BodyFatPercentViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(BodyFatPercentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_body_fat_percent_calculator, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Body Fat Percent Calculator"
        val application = requireNotNull(this.activity).application
        viewModelFactory = BodyFatPercentViewModelFactory(application)
        val sharedPreferences = application.applicationContext.getSharedPreferences("bodyFatDetails",Context.MODE_PRIVATE)

        val spinnerlist = arrayListOf<String>()
        val spinnerValues = arrayOf("Male", "Female")
        spinnerlist.addAll(spinnerValues)
        val spinnerAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item,spinnerlist)
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item)

        binding.genderSpinner.adapter = spinnerAdapter
        binding.genderSpinner.prompt = "Gender"

        val spinnerPosition = spinnerAdapter.getPosition(sharedPreferences.getString("gender","Male"))
        binding.genderSpinner.setSelection(spinnerPosition)
        binding.waistMeasureEditText.setText(sharedPreferences.getFloat("waistMeasurement",0f).toString())
        binding.neckMeasureEditText.setText(sharedPreferences.getFloat("neckMeasurement",0f).toString())
        binding.hipMeasureEditText.setText(sharedPreferences.getFloat("hipMeasurement",0f).toString())
        binding.heightMeasureEditText.setText(sharedPreferences.getFloat("height",0f).toString())
        binding.bodyFatTextView.text = resources.getString(R.string.your_body_fat_percentage,sharedPreferences.getFloat("bodyFatPercentage",0f),"%")

        binding.waistMeasureEditText.setCompoundDrawables(null,null, TextViewDrawable(resources,"cm"),null)
        binding.neckMeasureEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"cm"),null)
        binding.heightMeasureEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"cm"),null)

        binding.genderSpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long
            ) {
                if(parent.getItemAtPosition(pos)=="Male"){
                    binding.hipMeasureLayout.visibility = View.GONE
                }else{
                    binding.hipMeasureLayout.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        var bfp: Float
        binding.calculateButton.setOnClickListener {
            val gender = binding.genderSpinner.selectedItem.toString()
            if(gender=="Male"){
                bfp = (495/(1.0324-0.19077*
                        log10(binding.waistMeasureEditText.text.toString().toFloat()-
                                binding.neckMeasureEditText.text.toString().toFloat())+0.15456*
                        log10(binding.heightMeasureEditText.text.toString().toFloat())) - 450).toFloat()
            }else{
                bfp = (495/(1.29579-0.35004*
                        log10(binding.waistMeasureEditText.text.toString().toFloat()-
                                binding.neckMeasureEditText.text.toString().toFloat()+
                                binding.hipMeasureEditText.text.toString().toFloat())+0.22100*
                        log10(binding.heightMeasureEditText.text.toString().toFloat())) - 450).toFloat()
            }
            viewModel.store(bfp)
            binding.bodyFatTextView.text = resources.getString(R.string.your_body_fat_percentage,bfp,"%")
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("bodyFatDetails",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("gender",binding.genderSpinner.selectedItem.toString())
        editor.putFloat("waistMeasurement",binding.waistMeasureEditText.text.toString().toFloat())
        editor.putFloat("neckMeasurement",binding.neckMeasureEditText.text.toString().toFloat())
        editor.putFloat("hipMeasurement",binding.hipMeasureEditText.text.toString().toFloat())
        editor.putFloat("height",binding.heightMeasureEditText.text.toString().toFloat())
        editor.putFloat("bodyFatPercentage",viewModel.bodyFatValue.value!!)
        editor.apply()
    }
}