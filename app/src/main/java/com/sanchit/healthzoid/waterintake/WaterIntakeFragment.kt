package com.sanchit.healthzoid.waterintake

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.databinding.FragmentWaterIntakeBinding
import java.text.DateFormat
import java.util.*


class WaterIntakeFragment : Fragment() {
    private val calender = Calendar.getInstance()
    private lateinit var viewModelFactory: WaterIntakeViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(WaterIntakeViewModel::class.java)
    }
    private lateinit var binding: FragmentWaterIntakeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_water_intake,container,false)
        (activity as AppCompatActivity).supportActionBar?.title = "Water Intake"

        val application = requireNotNull(this.activity).application
        viewModelFactory  = WaterIntakeViewModelFactory(application)

        val sharedPreferences = application.applicationContext.getSharedPreferences("waterIntake",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        binding.currentWaterContentTextView.text = resources.getString(R.string.ml_text,sharedPreferences.getLong("waterIntakeValue", 0).toInt())
        val currentdate = DateFormat.getDateInstance().format(calender.time)
        if(currentdate != sharedPreferences.getString("PreviousDate",null)){
            if(sharedPreferences.getString("PreviousDate",null)!=null){
                val sharedPreferences2 = application.applicationContext.getSharedPreferences("waterIntake",Context.MODE_PRIVATE)
                sharedPreferences2.edit().putLong("waterIntakeValue", 0).apply()
                binding.currentWaterContentTextView.text = resources.getString(R.string.ml_text,sharedPreferences2.getLong("waterIntakeValue", 0).toInt())
                clear()
            }
        }
        editor.putString("PreviousDate", currentdate).apply()

        binding.waterEditTextChange.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    saveData()
                    return true
                }
                return false
            }
        })

        binding.waterEditTextChange.addTextChangedListener {
            if (it.toString() == "") {
                viewModel.change(0)
            } else {
                if(it!!.length==9){
                    Toast.makeText(context,"You cannot add another digit",Toast.LENGTH_SHORT).show()
                    if(it.toString()=="999999999")
                    Toast.makeText(context,"You have reached the max value",Toast.LENGTH_SHORT).show()
                }
                viewModel.change(it.toString().toLong())
            }
        }

        viewModel.waterIntake.observe(viewLifecycleOwner, Observer {
            when{
                it==0L -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.poor_water_content)
                        progressInformationImageView.text = "Please drink some water!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                    }
                }
                it in 1..500 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.half_filled_bottle)
                        bottleImageView2.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.poor_water_content)
                        progressInformationImageView.text = "Please drink some more water!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                    }
                }
                it in 501..1000 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.poor_water_content)
                        progressInformationImageView.text = "Please drink some more water!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                    }
                }
                it in 1001..1500 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.half_filled_bottle)
                        bottleImageView3.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.slightlypoor_water_content)
                        progressInformationImageView.text = "Water content is low!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.lightorange))
                    }
                }
                it in 1501..2000 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.slightlypoor_water_content)
                        progressInformationImageView.text = "Water content is low!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.lightorange))
                    }
                }
                it in 2001..2500 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.half_filled_bottle)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.okayish_water_content)
                        progressInformationImageView.text = "Mediocre content of water!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
                    }
                }
                it in 2501..3000 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.okayish_water_content)
                        progressInformationImageView.text = "Mediocre content of water!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
                    }
                }
                it in 3001..3500 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.half_filled_bottle)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.good_water_content)
                        progressInformationImageView.text = "Good content of water. Keep it up!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryLightColor))
                    }
                }
                it in 3501..4000 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
                        progressImageView.setImageResource(R.drawable.good_water_content)
                        progressInformationImageView.text = "Good content of water. Keep it up!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryLightColor))
                    }
                }
                it in 3001..3500 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.half_filled_bottle)
                        progressImageView.setImageResource(R.drawable.good_water_content)
                        progressInformationImageView.text = "Good content of water. Keep it up!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryLightColor))
                    }
                }
                it>3500 -> {
                    binding.apply {
                        bottleImageView1.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView2.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView3.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView4.setImageResource(R.drawable.fully_filled_bottle_image)
                        bottleImageView5.setImageResource(R.drawable.fully_filled_bottle_image)
                        progressImageView.setImageResource(R.drawable.good_water_content)
                        progressInformationImageView.text = "Good content of water. Keep it up!!"
                        progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.primaryLightColor))
                    }
                }
            }
        })
        return binding.root
    }

    private fun clear() {
        binding.apply {
            bottleImageView1.setImageResource(R.drawable.empty_bottle_image)
            bottleImageView2.setImageResource(R.drawable.empty_bottle_image)
            bottleImageView3.setImageResource(R.drawable.empty_bottle_image)
            bottleImageView4.setImageResource(R.drawable.empty_bottle_image)
            bottleImageView5.setImageResource(R.drawable.empty_bottle_image)
            progressImageView.setImageResource(R.drawable.poor_water_content)
            progressInformationImageView.text = "Please drink some water!!"
            progressInformationImageView.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
        }
        viewModel.clear()
        saveData()
    }

    override fun onResume() {
        super.onResume()
        val currentTime = DateFormat.getTimeInstance().format(calender.time)
        if(currentTime.substring(0,8)=="11:59:30"&&currentTime.substring(9)=="pm"){
            Toast.makeText(context,"Changes have been done to the app. Please restart", Toast.LENGTH_LONG).show()
        }
    }

    fun saveData(){
        binding.currentWaterContentTextView.text = resources.getString(R.string.ml_text,viewModel.waterIntake.value!!.toInt())
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("waterIntake", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("waterIntakeValue", viewModel.waterIntake.value!!).apply()
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }
}