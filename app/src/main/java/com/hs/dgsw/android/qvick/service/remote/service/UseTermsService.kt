package com.hs.dgsw.android.qvick.service.remote.service

import retrofit2.Call
import retrofit2.http.GET

interface UseTermsService {
    @GET("/terms/use-term")
    suspend fun getUseTerms():String
}