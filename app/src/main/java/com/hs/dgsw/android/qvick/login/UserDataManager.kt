package com.hs.dgsw.android.qvick.login

object UserDataManager {
    private var userId: String? = null
    private var password: String? = null

    fun setUserData(id: String, pass: String) {
        userId = id
        password = pass
    }

    fun getUserId(): String? {
        return userId
    }

    fun getPassword(): String? {
        return password
    }
}