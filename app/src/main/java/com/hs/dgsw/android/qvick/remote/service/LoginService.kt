package com.hs.dgsw.android.qvick.remote.service

import com.hs.dgsw.android.qvick.remote.request.LoginRequest
import com.hs.dgsw.android.qvick.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}