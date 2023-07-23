package com.discutions.app.interfaces

interface LoginStateListerner {

    fun onLoginSuccess();
    fun onLoginFailed(err:String);
}