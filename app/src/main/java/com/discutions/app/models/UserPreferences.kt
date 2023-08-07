package com.discutions.app.models

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "user-prefs"
        private const val KEY_EMAIL = "email"
        private const val KEY_USERNAME = "username"
        private const val KEY_USERID = "user-id"
        private const val KEY_PASSWORD = "password"
        private const val KEY_REMEMBER="remember";
        private const val KEY_AUTHSTATE="auth-state";
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()
    var userId: String?
        get() = sharedPreferences.getString(KEY_USERID, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERID, value).apply()
    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()
    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD, null)
        set(value) = sharedPreferences.edit().putString(KEY_PASSWORD, value).apply()
    var remember:Boolean?
        get() = sharedPreferences.getBoolean(KEY_REMEMBER,false);
        set(value) = sharedPreferences.edit().putBoolean(KEY_REMEMBER,value!!).apply();
    var isLogegd:Boolean?
        get() = sharedPreferences.getBoolean(KEY_AUTHSTATE,false);
        set(value) = sharedPreferences.edit().putBoolean(KEY_AUTHSTATE,value!!).apply();
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
