package com.discutions.app.models

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception
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