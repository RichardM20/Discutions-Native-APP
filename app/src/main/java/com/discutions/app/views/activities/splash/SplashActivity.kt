package com.discutions.app.views.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.models.UserPreferences
import com.discutions.app.views.activities.dashboard.DashboardActivity
import com.discutions.app.views.activities.login.LoginActivity
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : ComponentActivity() {
    private  lateinit var _prefs:UserPreferences;
    override fun onCreate(savedInstanceState: Bundle?) {
        _prefs = UserPreferences(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash);
        //
        redirect();
    }
    private fun redirect(){

        Timer().schedule(timerTask {
            if(_prefs.isLogged==false){
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }else{
                startActivity(Intent(applicationContext, DashboardActivity::class.java))
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2000)
    }
}
