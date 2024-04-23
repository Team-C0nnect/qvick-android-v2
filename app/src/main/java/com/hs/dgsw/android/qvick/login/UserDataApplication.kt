package com.hs.dgsw.android.qvick.login

import android.content.Context
import android.content.SharedPreferences

object UserDataApplication {
    private const val KEY_APPLICATION = "application"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(UserDataManager.PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setUserData(application: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_APPLICATION, application)
            apply()
        }
    }

    fun getApplication(): Boolean {
        return sharedPreferences.getBoolean(KEY_APPLICATION, false)
    }
}
