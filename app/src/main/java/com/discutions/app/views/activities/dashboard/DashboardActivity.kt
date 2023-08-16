package com.discutions.app.views.activities.dashboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

import com.discutions.app.R

import com.discutions.app.databinding.ActivityDashboardBinding
import com.discutions.app.models.UserPreferences
import com.discutions.app.views.fragments.create.CreateFragment
import com.discutions.app.views.fragments.home.HomeFragment
import com.discutions.app.views.fragments.notifications.NotificationsFragment
import com.discutions.app.views.fragments.profile.ProfileFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : AppCompatActivity()  {

    private lateinit var _binding: ActivityDashboardBinding

    private lateinit var bottomNav : BottomNavigationView;




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //
        _binding = ActivityDashboardBinding.inflate(layoutInflater) ;
        setContentView(_binding.root)
        val toolbar: Toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        loadFragment(HomeFragment());
        bottomNav = findViewById(R.id.bottomNavbar)

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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notification -> {

                loadFragment(NotificationsFragment());
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerScreen,fragment)
        fragmentTransaction.commit()
    }


}
