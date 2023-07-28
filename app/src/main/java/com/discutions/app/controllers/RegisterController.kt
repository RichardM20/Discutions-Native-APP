package com.discutions.app.controllers

import android.util.Patterns
import com.discutions.app.interfaces.RegisterStateListener
import com.discutions.app.models.FirebaseServices

class RegisterController {
    var email:String = "";
    var password:String="";
    var confirmPassword:String="";

    private val firebaseServices = FirebaseServices();


    //methods
    private fun isValidEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(): Boolean {
        return password.length >= 6
    }
    private fun isDifferentPassword():Boolean{
        return password==confirmPassword;
    }
    fun validateForm(callback:(String?)->Unit)  {
        if(!isDifferentPassword()){
           callback("different-password");
        }else{
            if(isValidEmail() && isValidPassword()){
                callback("valid-form")
            }else{
                callback("invalid-form")
            }
        }
    };

      fun registerWithEmailAndPassword(listener: RegisterStateListener){
          listener.onLoading(true);
          firebaseServices.registerWithEmailAndPassword(email, password){
              result->
              if(result?.errorMessage!=null){
                  listener.onRegisterFailed(result.errorMessage);
              }else{
                  listener.onRegisterSuccess();
              }
              listener.onLoading(false);
          }
    }

}