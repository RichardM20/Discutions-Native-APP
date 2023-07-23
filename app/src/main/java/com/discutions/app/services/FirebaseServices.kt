package com.discutions.app.services

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import java.util.logging.Handler

//servicios de firebase
//login, getData, sendData
class FirebaseServices {
    private lateinit var _auth: FirebaseAuth;
    fun initServices(context:android.content.Context){
        //verificamos la inicializacion de firebase
    if(FirebaseApp.getApps(context).isEmpty()){
        FirebaseApp.initializeApp(context);
    }
        _auth = Firebase.auth
    }



}