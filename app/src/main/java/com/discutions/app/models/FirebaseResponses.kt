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
                    "ERROR_INVALID_EMAIL" -> "Incorrect invalid"
                    "ERROR_WRONG_PASSWORD" -> "Incorrect password."
                    "ERROR_USER_NOT_FOUND" -> "Incorrect email or password"

                    else -> "Error unknown: ${ex.errorCode}"
                }
            }
            else -> "Error unknown: ${ex?.message ?: "Error unknown"}"
        }
    }
}