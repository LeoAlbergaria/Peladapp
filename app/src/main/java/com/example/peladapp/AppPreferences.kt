package com.example.peladapp

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val sp: SharedPreferences =
        context.getSharedPreferences("APP-KEY", Context.MODE_PRIVATE)

    private val userKey = "userKey"

    fun setUser(id: String) {
        sp.edit().putString(userKey, id).apply()
    }

    fun getUser(): String? {
        return sp.getString(userKey, null)
    }
}
