package com.sanchit.healthzoid.steptracker

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.databinding.FragmentStepTrackerBinding
import java.text.DateFormat
import java.util.*

class StepTrackerFragment : Fragment(), SensorEventListener {
    private val locationManager by lazy {
        activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val sensorManager by lazy {
        activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val calender = Calendar.getInstance()
    private val currentdate = DateFormat.getDateInstance().format(calender.time)

    private lateinit var binding: FragmentStepTrackerBinding
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_tracker, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Step Tracker"
        loadData()
        binding.stepsTakenTextView.setOnClickListener {
            Toast.makeText(activity, "Your current steps are ${binding.stepsTakenTextView.text}", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun clear() {
        binding.stepsTakenTextView.text = 0.toString()
        binding.distanceCoveredCounter.text = resources.getString(R.string.km_text, 0.00)
        binding.caloriesBurntCounter.text = resources.getString(R.string.cal_text, 0.00)
        binding.progressBar.apply {
            setProgressWithAnimation(0f)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStart() {
        requestLocationPermissions()
        super.onStart()
        when {
            isLocationPermissionGranted() -> {
                when {
                    isLocationEnabled() -> Toast.makeText(activity, "Location Enabled", Toast.LENGTH_SHORT).show()
                    else -> showLocationNotEnableDialog()
                }
            }
            else -> requestLocationPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(activity, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }

        val currenttime = DateFormat.getTimeInstance().format(calender.time)
        if (currenttime.substring(0, 8) == "11:59:30" && currenttime.substring(9) == "pm") {
            Toast.makeText(context, "Changes have been done to the app. Please restart", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("previousSteps", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("previousTotalSteps", previousTotalSteps)
        editor.putString("PreviousDate", currentdate)
        editor.apply()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(running) {
            totalSteps = event.values[0]

            val application = requireNotNull(this.activity).application
            val sharedPreferences = application.applicationContext.getSharedPreferences("previousSteps", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if (currentdate != sharedPreferences.getString("PreviousDate", null)) {
                if (sharedPreferences.getString("PreviousDate", null) != null) {
                    previousTotalSteps = totalSteps
                    editor.putString("PreviousDate",currentdate)
                    editor.putFloat("previousTotalSteps", previousTotalSteps)
                    editor.apply()
                    clear()
                }
            }

            val currentSteps: Float = totalSteps - previousTotalSteps
            binding.stepsTakenTextView.text = currentSteps.toInt().toString()
            binding.progressBar.apply {
                setProgressWithAnimation(((currentSteps) / 40))
            }
            binding.caloriesBurntCounter.text =
                resources.getString(R.string.cal_text, kcalories(currentSteps))
            binding.distanceCoveredCounter.text =
                resources.getString(R.string.km_text, distance(currentSteps) / 1000)
        }
    }

    private fun loadData() {
        val application = requireNotNull(this.activity).application
        val sharedPreferences = application.applicationContext.getSharedPreferences("previousSteps", Context.MODE_PRIVATE)
        previousTotalSteps = sharedPreferences.getFloat("previousTotalSteps", 0f)
    }

    private fun kcalories(step: Float): Double {
        return (step * 0.04)
    }

    private fun distance(step: Float): Double {
        return (step * 0.7)
    }

    private fun requestLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity?.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION),
                999
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun isLocationPermissionGranted(): Boolean {
        return (activity?.let {
            checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED
                && activity?.let { checkSelfPermission(it, Manifest.permission.ACTIVITY_RECOGNITION)
        } == PackageManager.PERMISSION_GRANTED)
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showLocationNotEnableDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Enable Location")
            .setMessage("Location is required for Tracking")
            .setCancelable(false)
            .setPositiveButton("Enable Now") { dialogInterface: DialogInterface, _: Int ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                dialogInterface.dismiss()
            }
            .show()
    }
}