package com.discutions.app.controllers

import com.discutions.app.interfaces.NotificationsDataState
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.models.FirebaseServices
import java.util.Timer
import kotlin.concurrent.timerTask

class NotificationsController {
    private val firebaseServices= FirebaseServices();
    fun getNotifications(listener: NotificationsDataState){
        listener.onLoading(true);
        Timer().schedule(timerTask {
            firebaseServices.getAllNotifications(
                onSuccess = {notifications->
                    listener.onLoading(false);
                    listener.onSuccess(notifications)
                },
                failed ={message->
                    listener.onLoading(false);
                    listener.onFailed(message);
                }
            );
            listener.onLoading(false);
        }, 1000)

    }
}