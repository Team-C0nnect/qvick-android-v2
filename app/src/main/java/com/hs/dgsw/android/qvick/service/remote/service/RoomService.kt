package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.RoomRequest
import com.hs.dgsw.android.qvick.service.remote.response.RoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("/room")
    suspend fun getRoom(

    ): RoomResponse
}