package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.StudentRequest
import com.hs.dgsw.android.qvick.service.remote.response.StudentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface StudentService {
    @POST("/student")
    suspend fun postStudent(
        @Body body: StudentRequest
    )

    @PUT("/student")
    suspend fun putStudent(
        @Body body: StudentRequest
    )

    @GET("/student")
    suspend fun getStudent(

    ): StudentResponse
}