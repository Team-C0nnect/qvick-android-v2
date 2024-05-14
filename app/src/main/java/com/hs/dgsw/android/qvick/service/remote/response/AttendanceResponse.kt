package com.hs.dgsw.android.qvick.service.remote.response

import com.google.gson.annotations.SerializedName

data class AttendanceResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("userId")
    val userId: Int,
    @field:SerializedName("checkedDate")
    val checkedDate: String,
)