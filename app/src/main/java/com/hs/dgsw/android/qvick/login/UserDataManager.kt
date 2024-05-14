package com.hs.dgsw.android.qvick.login

import android.content.Context
import android.content.SharedPreferences

object UserDataManager {
    const val PREF_NAME = "UserData"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_NAME = "name"
    private const val KEY_ROOM = "room"
    private const val KEY_STD_ID = "stdId"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setUserData(email: String?, password: String?, name: String, room: String, stdId: String) {
        sharedPreferences.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            putString(KEY_NAME, name)
            putString(KEY_ROOM, room)
            putString(KEY_STD_ID, stdId)
            apply()
        }
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }

    fun getName(): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getRoom(): String? {
        return sharedPreferences.getString(KEY_ROOM, null)
    }

    fun getStdId(): String? {
        return sharedPreferences.getString(KEY_STD_ID, null)
    }
}
