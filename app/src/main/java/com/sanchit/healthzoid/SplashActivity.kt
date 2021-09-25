package com.sanchit.healthzoid

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    val cm by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()
    }

    override fun onStart() {
        requestInternetpermission()
        super.onStart()
        when {
            isInternetpermissiongranted() -> {
                when{
                    isInternetEnabled() -> mainactivitycall()
                    else -> showInternetNotEnableDialog()
                }
            }
            else -> requestInternetpermission()
        }
    }

    private fun mainactivitycall() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finishAffinity()
        }, 1000)
    }

    private fun requestInternetpermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                arrayOf(Manifest.permission.INTERNET),
                999
            )
        }
    }

    private fun isInternetpermissiongranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }

    fun isInternetEnabled(): Boolean {
        var status = false
        val networkCapabilities = cm.activeNetwork
        val actNw = cm.getNetworkCapabilities(networkCapabilities)
        if (actNw != null) {
            if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                status = true
            } else if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                status = true
            }
        } else {
            status = false
        }
        return status
    }

    private fun showInternetNotEnableDialog() {
        AlertDialog.Builder(this)
            .setTitle("Enable Internet")
            .setMessage("Internet is required for this application")
            .setCancelable(false)
            .setPositiveButton("Enable Now") { dialogInterface: DialogInterface, i: Int ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                dialogInterface.dismiss()
            }
            .show()
    }
}