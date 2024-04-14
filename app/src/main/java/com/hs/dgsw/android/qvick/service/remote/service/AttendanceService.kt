package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.AttendanceRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AttendanceService {

    @POST("/attendance")
    suspend fun postAttendance(
        @Body body: AttendanceRequest
    )
}