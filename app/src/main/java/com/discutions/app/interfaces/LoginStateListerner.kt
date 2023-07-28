package com.discutions.app.interfaces

interface LoginStateListerner {

    fun onLoginSuccess(username:String?);
    fun onLoading(showLoading:Boolean);
    fun onLoginFailed(err:String);
}