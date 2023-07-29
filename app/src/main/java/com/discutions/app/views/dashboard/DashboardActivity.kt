package com.discutions.app.views.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.discutions.app.R
import com.discutions.app.controllers.DashboardController
import com.discutions.app.databinding.ActivityDashboardBinding

import com.discutions.app.models.UserPreferences
import com.discutions.app.views.dashboard.ui.create.CreateFragment
import com.discutions.app.views.dashboard.ui.home.HomeFragment
import com.discutions.app.views.dashboard.ui.profile.ProfileFragment

import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : ComponentActivity()  {

    private lateinit var _binding: ActivityDashboardBinding
    private lateinit var _dashboardController:DashboardController;
    private lateinit var bottomNav : BottomNavigationView;



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //
        _binding = ActivityDashboardBinding.inflate(layoutInflater) ;
        setContentView(_binding.root)
        _dashboardController= DashboardController(UserPreferences(this));
        loadFragment(HomeFragment());
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavbar)
        //
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_create -> {
                    loadFragment(CreateFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else ->  {
                    loadFragment(HomeFragment())
                    true
                }
            }
        }

    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = fragment.fragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerScreen,fragment)
        transaction?.commit()
    }


}
