package com.discutions.app.views.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.discutions.app.R
import com.discutions.app.views.ui.login.Login
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash);
        Timer().schedule(timerTask {
            startActivity(Intent(applicationContext, Login::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 2000)

    }
}
