package com.discutions.app.controllers

import com.discutions.app.interfaces.CompleteProfileState
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.ProfileData
import com.discutions.app.models.UserPreferences

class CompleteProfileController(private val userPreferences: UserPreferences) {
    var username:String="";
    var gender:String="";
    var description:String="";

    val firebaseServices:FirebaseServices =  FirebaseServices();
    fun completeProfile(listener:CompleteProfileState){
       try {
           firebaseServices.completeProfile(
               ProfileData(
                   username=username,
                   gender = gender,
                   email = userPreferences.email!!,
                   description = description,
                   uidUser = userPreferences.userId!!,
                   fcmToken = userPreferences.tokenFCM!!
               ),
               failed={
                   listener.onFailed(it);
               },
               success={
                   userPreferences.username=it?.username;
                   listener.onCompleted();
               }
           );
       }catch(e:Exception){
           listener.onFailed(e.message.toString());
       }
    }
}