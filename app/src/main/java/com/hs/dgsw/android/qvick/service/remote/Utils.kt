package com.hs.dgsw.android.qvick.service.remote

import android.content.Context
import android.content.SharedPreferences


object Utils {
    private const val PREFS = "prefs"
    private const val Access_Token = "Access_Token"
    private const val Refresh_Token = "Refresh_Token"
    private lateinit var mContext: Context
    private lateinit var prefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    fun init(context: Context) {
        mContext = context
        prefs = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefsEditor = prefs.edit()
    }

    fun setAccessToken(value: String) {
        prefsEditor.putString(Access_Token, value).apply()
    }

    fun getAccessToken(defValue: String): String {
        return prefs.getString(Access_Token, defValue) ?: defValue
    }

    fun setRefreshToken(value: String) {
        prefsEditor.putString(Refresh_Token, value).apply()
    }

    fun getRefreshToken(defValue: String): String {
        return prefs.getString(Refresh_Token, defValue) ?: defValue
    }

    fun clearToken() {
        prefsEditor.clear().apply()
    }
}
