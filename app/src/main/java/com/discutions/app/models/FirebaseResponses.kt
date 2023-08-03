package com.discutions.app.models

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import java.lang.Exception
import java.util.Date

data class PostDataResponse(
    val postData:PostData,
    val errorMessage:String?
)
data class PostData(
    val uidPost:String="",
    val publishedAt:Timestamp = Timestamp.now(),
    val username:String="",
    val post:String="",
    val comments:Int=0,
    val likes:Int=0,
){
    constructor() : this("", Timestamp.now(), "", "", 0, 0)
}
data class AuthResponse(
    val user:UserData?,
    val errorMessage:String?
)

data class UserData(
    val uid:String?,
    val email:String?
)
class ExceptionMessage {
    fun isException(ex: Exception):String?{
        return when (ex) {
            is FirebaseAuthException -> {
                when (ex.errorCode) {
                    "ERROR_WRONG_PASSWORD" -> "Incorrect password."
                    "ERROR_USER_NOT_FOUND" -> "Incorrect email or password"
                    "ERROR_WEAK_PASSWORD" -> "Weak password. It must be at least 6 characters long."
                    "ERROR_INVALID_EMAIL" -> "Invalid email address."
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "The email address is already in use by another user."
                    "ERROR_OPERATION_NOT_ALLOWED" -> "Account creation is not allowed at this time."
                    else -> "Error unknown: ${ex.errorCode}"
                }
            }
            else -> "Error unknown: ${ex?.message ?: "Error unknown"}"
        }
    }
}