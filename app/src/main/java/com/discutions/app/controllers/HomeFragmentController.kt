package com.discutions.app.controllers

import com.discutions.app.interfaces.OnDataCommentsListener
import com.discutions.app.interfaces.OnDataPostsListener
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences
import com.discutions.app.views.adapters.HomeFragmentAdapter
import java.util.Timer
import kotlin.concurrent.timerTask

class HomeFragmentController(){
   private val firebaseServices = FirebaseServices();
    fun getPosts(listener: OnDataPostsListener){
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
    fun comment(comment:String){
      if(comment.isEmpty()){
          println("Empty");
      }else{
          firebaseServices.publishComment(comment);
      }
    }

}