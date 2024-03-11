package com.hs.dgsw.android.qvick.remote.response

import com.google.gson.annotations.SerializedName

data class RoomResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("userId")
    val userID: Int,
    @field:SerializedName("roomId")
    val roomID: String
)