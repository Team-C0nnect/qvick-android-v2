package com.hs.dgsw.android.qvick.service.remote.request

import com.google.gson.annotations.SerializedName


// 요구
data class LoginRequest(
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String
)