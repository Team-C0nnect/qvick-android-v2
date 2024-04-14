package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.TokenRequest
import com.hs.dgsw.android.qvick.service.remote.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("/auth/refresh")
    suspend fun postRefreshToken(
        @Body body: TokenRequest
    ): TokenResponse
}