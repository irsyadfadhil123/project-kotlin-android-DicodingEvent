package com.example.dicodingevent.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dicodingevent.R
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.ActivityBottomNavigationBinding
import com.example.dicodingevent.ui.fragment.finished.FinishedViewModel
import com.example.dicodingevent.ui.fragment.home.HomeViewModel
import com.example.dicodingevent.ui.fragment.upcoming.UpcomingViewModel

class BottomNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private val upcomingViewModel by viewModels<UpcomingViewModel>()
    private val finishedViewModel by viewModels<FinishedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_upcoming, R.id.navigation_finished
            )
        )
        navView.setupWithNavController(navController)
    }
}