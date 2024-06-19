package com.hs.dgsw.android.qvick.service.remote.response

import com.google.gson.annotations.SerializedName


// 응답
//200: 성공, 300,400: 에러
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)