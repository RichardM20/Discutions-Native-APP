package com.discutions.app

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.discutions.app.controllers.PushNotificationFirebaseService
import com.discutions.app.models.UserPreferences

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this);
        val service = PushNotificationFirebaseService(this);
        service.getNotification();
    }

}