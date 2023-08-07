package com.discutions.app.controllers

import android.util.Patterns
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.models.FirebaseServices
import com.discutions.app.models.UserPreferences

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
              result->
              if(result?.errorMessage!=null){
                  listener.onLoginFailed(result.errorMessage);
              }else{
                println("USER ID: ${result?.user?.uid}");
                  preferences.userId=result?.user?.uid;
                  listener.onLoginSuccess("${result?.user?.email}");
              }
              listener.onLoading(false);
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
                       preferences.isLogegd=true;
                       listener.onLoginSuccess("${user.displayName}");
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