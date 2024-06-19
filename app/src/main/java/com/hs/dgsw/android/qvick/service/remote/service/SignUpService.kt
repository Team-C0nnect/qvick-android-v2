package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.SignUpRequest
import com.hs.dgsw.android.qvick.service.remote.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/auth/sign-up")
    suspend fun postSignUp(
        @Body body: SignUpRequest
    ):BaseResponse<Unit>
}