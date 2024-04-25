package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.login.SignUpActivity
import com.hs.dgsw.android.qvick.service.remote.request.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/auth/sign-up")
    suspend fun postSignUp(
        @Body body: SignUpRequest
    )
}