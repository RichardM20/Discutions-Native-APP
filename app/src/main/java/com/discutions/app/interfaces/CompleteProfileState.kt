package com.discutions.app.interfaces

interface CompleteProfileState {
    fun onLoading(showLoading:Boolean);
    fun onCompleted();
    fun onFailed(errorMessage:String);
}