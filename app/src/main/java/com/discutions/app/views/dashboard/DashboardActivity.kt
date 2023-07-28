package com.discutions.app.views.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.databinding.ActivityDashboardBinding
import com.discutions.app.interfaces.LogoutState
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences
import com.discutions.app.views.login.LoginActivity


class DashboardActivity : ComponentActivity(), LogoutState {

    private lateinit var _binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        _binding = ActivityDashboardBinding.inflate(layoutInflater) ;
        setContentView(_binding.root)

        logOut();
    }

    private fun logOut(){
        _binding.buttonLogout.setOnClickListener {
            FirebaseServices().logOut();
            this.logout(true);
        }
    }

    override fun logout(logout: Boolean) {
        UserPreferences(this).isLogegd=false;
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish();
    }
}
