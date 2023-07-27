package com.discutions.app.controllers

import android.util.Patterns
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.models.FirebaseServices

class LoginController {
      var email:String = "";
      var password:String="";
    private val firebaseServices = FirebaseServices();
    private fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(): Boolean {
        return password.length >= 4
    }
    fun validateForm( ):Boolean {
        return isValidEmail() && isValidPassword( )
    }

      fun loginWithEmailAndPassword(listener: LoginStateListerner){
          listener.onLoading(true);
          firebaseServices.loginWithEmailAndPassword(email, password){
              result->
              if(result!="Success"){
                  listener.onLoginFailed(result!!);
              }else{
                  listener.onLoginSuccess();
              }
              listener.onLoading(false);
          }
    }
    fun loginWithGoogle(idToken: String,listener: LoginStateListerner) {
        listener.onLoading(true);
        firebaseServices.signInWithGoogle(idToken) { result ->
            if (result=="success") {
               listener.onLoginSuccess();
            } else {
                listener.onLoginFailed(result);
            }
            listener.onLoading(false);
        }
    }

}