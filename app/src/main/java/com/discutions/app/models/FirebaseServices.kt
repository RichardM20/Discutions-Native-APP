package com.discutions.app.models

import com.discutions.app.interfaces.LoginStateListerner
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.lang.Exception
import kotlin.concurrent.timerTask

//servicios de firebase
//login, getData, sendData
class FirebaseServices {
    private var _auth = Firebase.auth
    private val _response = FirebaseResponses();


    //methods
      fun loginWithEmailAndPassword(email:String, password:String, listener:LoginStateListerner) {

         _auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
             task->
                if(task.isSuccessful){
                  listener.onLoginSuccess();
                }else{
                    val ex =_response.authResponse(task.exception);
                    listener.onLoginFailed(ex!!);
                }
         }.addOnCanceledListener {
           timerTask {
               listener.onLoginCancel();
           }
         }
    }
}