package com.example.peladapp

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility class for managing application preferences.
 *
 * This class provides methods to store and retrieve user-related information in SharedPreferences.
 *
 * @property context The application context.
 */
class AppPreferences(context: Context) {

    private val sp: SharedPreferences =
        context.getSharedPreferences("APP-KEY", Context.MODE_PRIVATE)

    private val userKey = "userKey"

    /**
     * Sets the user ID in SharedPreferences.
     *
     * @param id The user ID to be stored.
     */
    fun setUser(id: String) {
        sp.edit().putString(userKey, id).apply()
    }

    /**
     * Retrieves the stored user ID from SharedPreferences.
     *
     * @return The stored user ID, or null if not found.
     */
    fun getUser(): String? {
        return sp.getString(userKey, null)
    }

    /**
     * Logs out the user by removing the stored user ID from SharedPreferences.
     */
    fun logOutUser() {
        sp.edit().putString(userKey, null).apply()
    }

    companion object {
        private var instance: AppPreferences? = null

        /**
         * Gets the singleton instance of AppPreferences.
         *
         * @param context The application context.
         * @return The AppPreferences instance.
         */
        fun getInstance(context: Context): AppPreferences {
            if (instance == null) {
                instance = AppPreferences(context)
            }
            return instance!!
        }
    }
}
