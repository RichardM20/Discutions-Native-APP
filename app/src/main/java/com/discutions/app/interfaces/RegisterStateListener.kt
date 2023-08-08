package com.discutions.app.interfaces

interface RegisterStateListener {

    fun onRegisterSuccess(userExist:Boolean);
    fun onLoading(showLoading:Boolean);
    fun onRegisterFailed(err:String);
}