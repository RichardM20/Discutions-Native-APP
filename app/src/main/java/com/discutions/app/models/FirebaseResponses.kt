package com.discutions.app.models

import com.google.firebase.auth.FirebaseAuthException
import java.lang.Exception

class FirebaseResponses {
    fun authResponse(ex:Exception?):String?{
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