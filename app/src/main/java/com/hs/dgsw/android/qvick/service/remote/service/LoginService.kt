package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.LoginRequest
import com.hs.dgsw.android.qvick.service.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/sign-in")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}