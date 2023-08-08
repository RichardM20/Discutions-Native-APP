package com.discutions.app.services

import com.discutions.app.interfaces.HttpService
import com.discutions.app.models.FirebaseServices

import com.discutions.app.models.NotificationData
import com.discutions.app.models.NotificationModel
import com.discutions.app.models.NotificationsData

import com.google.api.Http
import com.google.firebase.Timestamp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val BASE_URL = "https://fcm.googleapis.com/"
class FCMService {

        companion object{
            fun sendNotification(token: String, title: String, message: String,callback:(String)->Unit) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiService = retrofit.create(HttpService::class.java);

                val notificationModel = NotificationModel(
                    to=token,
                    notification = NotificationData(
                        title = title,
                        body = message
                    )
                )
                val call = apiService.sendNotification(notificationModel)
                call.enqueue(object : Callback<Any> {
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                      println("success send notification");
                        println(response.code())
                        println(response.message())
                        println(response.raw())
                        callback("success");
                    }
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                       println("Failed")
                        println(t.message);
                        callback(t.message.toString());
                    }
                })
            }
        }



}