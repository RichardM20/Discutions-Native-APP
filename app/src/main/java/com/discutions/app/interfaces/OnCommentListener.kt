package com.discutions.app.interfaces

interface OnCommentListener {
    fun onSuccess();

    fun onFailed(errorMessage:String);

}