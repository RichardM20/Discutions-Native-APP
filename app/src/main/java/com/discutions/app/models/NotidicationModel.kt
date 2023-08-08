package com.discutions.app.models

data class NotificationModel(
    val to: String,
    val notification: NotificationData
)
data class NotificationData(
    val title:String,
    val body:String
)

