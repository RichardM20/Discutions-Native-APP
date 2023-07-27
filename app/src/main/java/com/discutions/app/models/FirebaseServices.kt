package com.discutions.app.models


import android.content.Context
import android.content.Intent
import com.discutions.app.R
import com.discutions.app.interfaces.LoginStateListerner
import com.discutions.app.views.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Timer

import kotlin.concurrent.timerTask

//servicios de firebase
//login, getData, sendData
class FirebaseServices {
    private var _auth:FirebaseAuth = Firebase.auth
    private val _response = ExceptionMessage();



    //methods
      fun loginWithEmailAndPassword(email: String, password: String,callback:(String?)->Unit) {
        _auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                    result->
                if(result.isSuccessful){
                   callback("Success");
                } else {
                    val message = _response.isException(result.exception!!);
                    callback(message);
                }
            }
      }



    fun signInWithGoogle(idToken: String, callback: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        _auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
               if(task.isSuccessful) callback("Success") else callback(task.exception?.message!!);
            }
    }
}