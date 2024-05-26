package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.AttendanceRequest
import com.hs.dgsw.android.qvick.service.remote.response.AttendanceResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AttendanceService {
    @POST("/attendance")
    suspend fun postAttendance(
        @Header("authorization") accessToken : String,
        @Body body: AttendanceRequest
    )

    @GET("/attendance")
    suspend fun getAttendance(
        @Header("authorization") accessToken : String
    ):AttendanceResponse
}