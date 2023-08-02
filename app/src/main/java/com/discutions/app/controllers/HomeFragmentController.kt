package com.discutions.app.controllers

import com.discutions.app.interfaces.OnDataFetchListener
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences

class HomeFragmentController {
   private val firebaseServices = FirebaseServices();
    fun getPosts(listener: OnDataFetchListener){

        listener.onLoading(true);
//        firebaseServices.getAllPosts()
    }

}