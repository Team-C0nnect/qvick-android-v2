package com.hs.dgsw.android.qvick.remote.service

import retrofit2.Call
import retrofit2.http.GET

interface UseTermsService {
    @GET("/terms/use-term")
    suspend fun getUseTerms(

    ):Call<String>
}