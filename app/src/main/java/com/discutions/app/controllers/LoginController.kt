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
            firebaseServices.loginWithEmailAndPassword(email, password,listener);
    }
}