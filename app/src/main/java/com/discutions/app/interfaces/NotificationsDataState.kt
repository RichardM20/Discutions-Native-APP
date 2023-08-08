package com.discutions.app.interfaces

import com.discutions.app.models.NotificationsData

interface NotificationsDataState {

    fun onLoading(isLoading:Boolean)
    fun onSuccess(notifications: List<NotificationsData>);
    fun onFailed(errorMessage:String);
}