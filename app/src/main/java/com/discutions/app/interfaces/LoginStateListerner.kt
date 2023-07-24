package com.discutions.app.interfaces

interface LoginStateListerner {

    fun onLoginSuccess();
    fun onLoginCancel();
    fun onLoginFailed(err:String);
}