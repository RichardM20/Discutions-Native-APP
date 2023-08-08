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
        PushNotificationFirebaseService();
        getTokenFCM(UserPreferences(this));

    }
    private fun getTokenFCM(preferences: UserPreferences){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
                result->
            if(result.isSuccessful){
                if(preferences.tokenFCM!=result.result.toString()){
                    preferences.tokenFCM=result.result.toString();
                }else{
                    println("already saved token");
                }
                print("prefs fcm: ${preferences.tokenFCM}");
            }

        }
    }

}