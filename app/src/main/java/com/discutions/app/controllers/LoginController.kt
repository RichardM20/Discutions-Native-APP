package com.discutions.app.controllers

import android.util.Patterns

class LoginController {
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 4
    }
    fun onLoginButtonClick(email:String, password:String):Boolean {
        return isValidEmail(email) && isValidPassword(password)
    }
}