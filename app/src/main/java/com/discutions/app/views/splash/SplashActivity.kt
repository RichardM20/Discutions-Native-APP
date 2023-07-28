package com.discutions.app.views.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.models.UserPreferences
import com.discutions.app.views.dashboard.DashboardActivity
import com.discutions.app.views.login.LoginActivity
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
        println("Nav screen");
        Timer().schedule(timerTask {
            if(_prefs.isLogegd==false){
                println("logged");
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }else{
                println("not logged");
                startActivity(Intent(applicationContext, DashboardActivity::class.java))
            }
            println("log state: ${_prefs.isLogegd}");
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2000)
    }
}
