package com.discutions.app.controllers

import com.discutions.app.interfaces.PostState
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.PostData
import com.discutions.app.models.UserPreferences
import com.google.firebase.Timestamp

class CreateController(private  val userPreferences: UserPreferences) {
    private val firebaseServices=FirebaseServices();
    var post:String="";

    fun post(listener:PostState){
       try {
           firebaseServices.post(
               PostData(
                   uidPost = "none",
                   fcmToken = userPreferences.tokenFCM!!,
                   post = post,
                   uidUser = userPreferences.userId!!,
                   username = userPreferences.username!!,
                   likes = emptyList(),
                   comments = emptyList(),
                   publishedAt = Timestamp.now(),
               ),
               callback = {
                       result->
                   if(result!="success"){
                       listener.onFailed(result);
                   }else{
                       listener.onSuccess();
                   }
               }
           )
       }catch(e:Exception){
           listener.onFailed(e.message.toString());
       }
    }
}