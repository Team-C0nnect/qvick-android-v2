package com.hs.dgsw.android.qvick.service.remote.response

import com.google.gson.annotations.SerializedName

data class AttendanceResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("userId")
    val userId: Int,
    @field:SerializedName("stdId")
    val stdId: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("room")
    val room: String,
    @field:SerializedName("checkedDate")
    val checkedDate: String,
)