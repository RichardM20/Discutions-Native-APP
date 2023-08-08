package com.discutions.app.controllers

import android.util.Patterns
import com.discutions.app.interfaces.RegisterStateListener
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences

class RegisterController(val preferences: UserPreferences) {
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
           callback("Passwords do not match");
        }else{
            if(isValidEmail() && isValidPassword()){
                callback("valid-form")
            }else{
                callback("Email or password invalid")
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
                  preferences.userId=result?.user?.uid;
                  preferences.email=result?.user?.email;
                  firebaseServices.addUser(result?.user?.uid.toString()) { result ->
                      if (result != "exist") {
                          listener.onLoading(false);
                          listener.onRegisterSuccess(false);
                      } else {

                          listener.onLoading(false);
                          listener.onRegisterSuccess(true);
                      }
                  };

              }
              listener.onLoading(false);
          }
    }

}