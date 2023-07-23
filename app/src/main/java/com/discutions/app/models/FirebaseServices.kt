package com.discutions.app.models

import com.discutions.app.interfaces.LoginStateListerner
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

//servicios de firebase
//login, getData, sendData
class FirebaseServices {
    private var _auth = Firebase.auth

    //methods
      fun loginWithEmailAndPassword(email:String, password:String, listener:LoginStateListerner) {
        println("login calledd");
         _auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
             task->
             println("login completed");
            try {
                if(task.isSuccessful){
                    println("success");
                  listener.onLoginSuccess();
                }
               else  if(task.isCanceled){
                     println("cancel");
                     listener.onLoginFailed("cancel");
                }
            }catch(ex:Exception){
                println("Exception");
                val err = (ex as? FirebaseAuthException)?.errorCode
                listener.onLoginFailed("ERR: ${err!!}");
            }
         }
    }
}