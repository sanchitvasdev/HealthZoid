package com.sanchit.healthzoid

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.sanchit.healthzoid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        @Suppress("UNUSED_VARIABLE")
        val binding= DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.overflow_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        when(item.itemId) {
            R.id.todaysFactFragment->   NavigationUI.onNavDestinationSelected(item,navController)
            R.id.reviewUsFragment -> NavigationUI.onNavDestinationSelected(item,navController)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        when(item.itemId){
            R.id.dailyDietFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.waterIntakeFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.bodyFatPercentCalculatorFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.stepTrackerFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.goalCaloriesFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.yourBmrAndTeeFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.volumeCalculatorFragment -> NavigationUI.onNavDestinationSelected(item,navController)
            R.id.activityCaloriesFragment -> NavigationUI.onNavDestinationSelected(item,navController)
        }
        return true
    }
}