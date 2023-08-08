package com.discutions.app.interfaces


import com.discutions.app.models.NotificationModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface HttpService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: key=AAAA4Y-9kUo:APA91bFpnO-3hOMi7_T3616lwyd52F2UkTksWkiseZjbDJ3sAXcffTVCXpRIUYLfOqM1siUcZESVPq97jW65cjeIsQydyz725DcwEKx_TclNlOnUiwLVZ90kvxPwZ3unchyucROZ7X39"
    )
    @POST("fcm/send")
    fun sendNotification(@Body notification: NotificationModel): Call<Any>;
}