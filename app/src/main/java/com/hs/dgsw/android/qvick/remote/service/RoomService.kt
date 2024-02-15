package com.hs.dgsw.android.qvick.remote.service

import com.hs.dgsw.android.qvick.remote.request.RoomRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface RoomService {
    @POST("/room")
    suspend fun postRoom(
        @Body body: RoomRequest
    )

    @PUT("/room")
    suspend fun putRoom(
        @Body body: RoomRequest
    )
}