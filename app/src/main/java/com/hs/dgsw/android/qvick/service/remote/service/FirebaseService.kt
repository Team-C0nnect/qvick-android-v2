package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.FirebaseRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FirebaseService {
    @POST("/auth/firebase")
    suspend fun postFcm(
        @Header("authorization") accessToken : String,
        @Body body: FirebaseRequest
    )
}