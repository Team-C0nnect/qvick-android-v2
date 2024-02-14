package com.hs.dgsw.android.qvick.remote.service

import com.hs.dgsw.android.qvick.remote.request.RoomRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomService {
    @POST("/room")
    suspend fun postRoom(
        @Body body: RoomRequest
    )
}