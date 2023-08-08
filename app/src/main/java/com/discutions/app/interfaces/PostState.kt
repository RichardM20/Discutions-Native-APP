package com.discutions.app.interfaces

interface PostState {
    fun onLoading(isLoading:Boolean);
    fun onSuccess();
    fun onFailed(errorMessage:String);

}