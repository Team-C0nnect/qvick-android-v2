package com.hs.dgsw.android.qvick.service.remote.service

import retrofit2.Call
import retrofit2.http.GET

interface PrivacyTermsService {
    @GET("/terms/privacy-policy")
    suspend fun getPrivacyTerms():String
}