package com.discutions.app.interfaces

interface LoginStateListerner {

    fun onLoginSuccess(haveAccount:Boolean);
    fun onLoading(showLoading:Boolean);
    fun onLoginFailed(err:String);
}