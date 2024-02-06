package com.hs.dgsw.android.qvick.remote.service

import com.hs.dgsw.android.qvick.remote.request.TokenRequest
import com.hs.dgsw.android.qvick.remote.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("/auth/refresh")
    suspend fun postRefreshToken(
        @Body body: TokenRequest
    ): TokenResponse
}