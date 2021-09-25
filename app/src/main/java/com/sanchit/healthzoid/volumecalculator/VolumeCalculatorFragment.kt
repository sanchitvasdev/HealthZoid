package com.sanchit.healthzoid.volumecalculator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.customDrawables.TextViewDrawable
import com.sanchit.healthzoid.databinding.FragmentVolumeCalculatorBinding

class VolumeCalculatorFragment : Fragment() {
    private lateinit var binding: FragmentVolumeCalculatorBinding
    private lateinit var viewModelFactory: VolumeCalculatorViewModelFactory
    private val viewModel: VolumeCalculatorViewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(VolumeCalculatorViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_volume_calculator, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Volume Calculator"
        val application = requireNotNull(this.activity).application
        viewModelFactory = VolumeCalculatorViewModelFactory(application)
        val sharedPreferences = application.applicationContext.getSharedPreferences("volumeDetails",Context.MODE_PRIVATE)

        binding.weightLiftedEditText.setCompoundDrawables(null,null,TextViewDrawable(resources,"kg"),null)
        binding.setsEditText.setText(sharedPreferences.getInt("sets",0).toString())
        binding.repsEditText.setText(sharedPreferences.getInt("reps",0).toString())
        binding.weightLiftedEditText.setText(sharedPreferences.getFloat("weightLifted",0f).toString())
        binding.workoutVolumeTextView.text = resources.getString(R.string.your_workout_volume_is,sharedPreferences.getFloat("volumeValue",0f))

        binding.calculateButton4.setOnClickListener {
            val volume = (binding.setsEditText.text.toString().toFloat()*
                    binding.repsEditText.text.toString().toFloat()*
                    binding.weightLiftedEditText.text.toString().toFloat())
            binding.workoutVolumeTextView.text = resources.getString(R.string.your_workout_volume_is,volume)
            viewModel.store(volume)
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("volumeDetails",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("sets",binding.setsEditText.text.toString().toInt())
        editor.putInt("reps",binding.repsEditText.text.toString().toInt())
        editor.putFloat("weightLifted",binding.weightLiftedEditText.text.toString().toFloat())
        editor.putFloat("volumeValue",viewModel.volumeValue.value!!)
        editor.apply()
    }
}