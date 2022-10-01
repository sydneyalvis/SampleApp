package com.example.popup.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val APP_PREF = "app_preferences"

     val USERNAME = "username"
     val EMAIL = "email"
     val PASSWORD = "password"

    private val LOGGEDIN = "loggedin"

    private val preferences: SharedPreferences = context.getSharedPreferences( APP_PREF , 0)

    private val editor = preferences.edit()

    private fun storeString(key: String, value: String) {
        editor.run {
            putString(key, value)
            apply()
        }
    }


    fun getString(key: String) =
        preferences.getString(key, "")

    private fun storeBoolean(key: String, value: Boolean) =
        editor.run {
            putBoolean(key, value)
            apply()
        }

    fun setLogin(){
        storeBoolean(LOGGEDIN, true)
    }

    fun setLogOut(){
        storeBoolean(LOGGEDIN, false)
    }

    fun setUserName(name: String){
        storeString(USERNAME, name)
    }

    fun setEmail(email: String){
        storeString(EMAIL, email)
    }

    fun setPassword(password: String){
        storeString(PASSWORD, password)
    }

    fun isLoggedIn()= preferences.getBoolean(LOGGEDIN, false)

    fun isNotLoggedIn()= !isLoggedIn()

    fun getUserName(): String?  {
        val str=getString(USERNAME)
        if (str.isNullOrBlank())
            return null
        return str
    }


}