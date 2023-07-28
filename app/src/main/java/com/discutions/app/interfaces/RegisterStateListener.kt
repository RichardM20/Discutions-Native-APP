package com.discutions.app.interfaces

interface RegisterStateListener {

    fun onRegisterSuccess();
    fun onLoading(showLoading:Boolean);
    fun onRegisterFailed(err:String);
}