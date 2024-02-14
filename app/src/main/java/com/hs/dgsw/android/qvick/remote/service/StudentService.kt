package com.hs.dgsw.android.qvick.remote.service

import com.hs.dgsw.android.qvick.remote.request.StudentRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface StudentService {
    @POST("/student")
    suspend fun postStudent(
        @Body body: StudentRequest
    )
}