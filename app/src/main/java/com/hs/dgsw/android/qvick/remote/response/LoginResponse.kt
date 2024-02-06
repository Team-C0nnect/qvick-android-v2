package com.hs.dgsw.android.qvick.remote.response

import com.google.gson.annotations.SerializedName


// 응답
//200: 성공, 300,400: 에러
data class LoginResponse(
    @field:SerializedName("accessToken")
    val accessToken: String,
    @field:SerializedName("refreshToken")
    val refreshToken: String
)