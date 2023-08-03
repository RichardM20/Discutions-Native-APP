package com.discutions.app.controllers

import com.discutions.app.interfaces.OnDataFetchListener
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences
import com.discutions.app.views.adapters.HomeFragmentAdapter
import java.util.Timer
import kotlin.concurrent.timerTask

class HomeFragmentController{
   private val firebaseServices = FirebaseServices();
    fun getPosts(listener: OnDataFetchListener){
        listener.onLoading(true);
        Timer().schedule(timerTask {
            firebaseServices.getAllPosts(
                onSuccess = {posts->
                        listener.onLoading(false);
                        listener.onDataFetchSuccess(posts);
                },
                failed ={message->
                    listener.onLoading(false);
                    listener.onDataFetchFailure(message);
                }
            );
            listener.onLoading(false);
        }, 1000)




    }

}