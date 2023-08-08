package com.discutions.app.controllers

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.discutions.app.MyApplication
import com.discutions.app.R
import com.discutions.app.models.UserPreferences
import com.google.firebase.messaging.Constants.MessageNotificationKeys.CHANNEL
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val  CHANNEL_ID="NOTIFICATION_CHANNEL";
const val CHANNEL_NAME="com.discutions.app"
class PushNotificationFirebaseService() : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

     try {
        if(remoteMessage.notification!=null){
            println("notification: ${remoteMessage.notification?.body}");

        }

     }catch(e:Exception){
         println("e: $e");
     }
    }

    override fun onNewToken(token: String) {

        println( "Refreshed token: $token")
    }

   private fun showNotification(title:String, content:String){
        val intent = Intent(this, MyApplication::class.java);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        val pendingIntent:PendingIntent;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
        }else{
            pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE);
        }

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.discussion)
            .setAutoCancel(false)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent);

        builder= builder.setContent(getRemoteView(title,content));
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0,builder.build());
    }
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title:String, content:String):RemoteViews{
        val remoteView = RemoteViews("com.discutions.app", R.layout.notifications_content)
        remoteView.setTextViewText(R.id.titleNotification, title);
        remoteView.setTextViewText(R.id.bodyNotification, content);
        remoteView.setImageViewResource(R.id.iconNotificationApp, R.drawable.discussion);
        return remoteView;
    }
}
