package com.hs.dgsw.android.qvick.remote.service

import com.hs.dgsw.android.qvick.remote.request.StudentRequest
import retrofit2.http.Body
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
}