package com.hs.dgsw.android.qvick.remote.request

import com.google.gson.annotations.SerializedName

data class RoomRequest (
    @field:SerializedName("roomId")
    val roomId : String
)