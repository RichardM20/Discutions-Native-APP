package com.discutions.app.interfaces

interface LoginStateListerner {

    fun onLoginSuccess();
    fun onLoading(showLoading:Boolean);
    fun onLoginFailed(err:String);
}