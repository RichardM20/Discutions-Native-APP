package com.discutions.app.controllers

import android.util.Patterns
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences
import com.discutions.app.services.FCMService

class LoginController(val preferences: UserPreferences) {
      var email:String = "";
      var password:String="";
    private val firebaseServices = FirebaseServices();
    private fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(): Boolean {
        return password.length >= 6
    }
    fun validateForm( ):Boolean {
        return isValidEmail() && isValidPassword( )
    }

      fun loginWithEmailAndPassword(listener: LoginStateListerner){
          listener.onLoading(true);
          firebaseServices.loginWithEmailAndPassword(email, password){
              userResult->
              if(userResult?.errorMessage!=null){
                  listener.onLoading(false);
                  listener.onLoginFailed(userResult.errorMessage);
              }else{
                  firebaseServices.addUser(userResult?.user?.uid.toString()) { result ->
                        preferences.userId=userResult?.user?.uid;
                        preferences.email=userResult?.user?.email;
                      if (result != "exist") {
                            listener.onLoading(false);
                            listener.onLoginSuccess(false);
                        } else {
                            listener.onLoading(false);
                            listener.onLoginSuccess(true);
                        }
                  };
              }

          }
    }
    fun loginWithGoogle(idToken: String,listener: LoginStateListerner) {
        //probando las maneras de implementaciones posibles
        //en este metodo lo maneje un poco diferente utilizando el fold
        listener.onLoading(true);
        firebaseServices.signInWithGoogle(idToken) { result ->
           result.fold(
               onSuccess = {user->
                   if(user!=null){
                       preferences.userId=user.uid
                       preferences.email=user.email
                       firebaseServices.addUser(user.uid) { result ->
                           if (result != "exist") {

                               listener.onLoginSuccess(false);
                           } else {
                               listener.onLoginSuccess(true);
                           }
                       };
                   }else{
                       listener.onLoginFailed("User not found or not exist")
                   }
                   listener.onLoading(false);
               },
               onFailure = {exception ->
                   listener.onLoginFailed(exception.message!!)
                   listener.onLoading(false);
               }
           )
        }
    }

}