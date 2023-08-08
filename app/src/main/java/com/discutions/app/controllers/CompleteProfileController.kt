package com.discutions.app.controllers

import android.util.Patterns
import com.discutions.app.interfaces.CompleteProfileState
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.ProfileData
import com.discutions.app.models.UserPreferences

class CompleteProfileController(private val userPreferences: UserPreferences) {
    var username:String="";
    var gender:String="";
    var description:String="";

    private val firebaseServices:FirebaseServices =  FirebaseServices();

    private fun isValidUsername(): Boolean {
        return username.length>=3;
    }

    private fun isValidDescription(): Boolean {
        return description.length >=3
    }
    fun validateForm( ):Boolean {
        return isValidUsername() && isValidDescription( )
    }


    fun completeProfile(listener:CompleteProfileState){
        listener.onLoading(true);
       try {
           firebaseServices.completeProfile(
               ProfileData(
                   username=username.ifEmpty { "Unknown" },
                   gender = gender.ifEmpty { "none" },
                   email = userPreferences.email!!,
                   description = description,
                   uidUser = userPreferences.userId!!,
                   fcmToken = userPreferences.tokenFCM!!
               ),
               failed={
                   listener.onLoading(false);
                   listener.onFailed(it);
               },
               success={
                   userPreferences.username=it?.username;
                   userPreferences.isLogged=true;
                   listener.onLoading(false);
                   listener.onCompleted();

               }
           );
       }catch(e:Exception){
           println(e);
           listener.onLoading(false);
           listener.onFailed(e.message.toString());

       }
    }
}