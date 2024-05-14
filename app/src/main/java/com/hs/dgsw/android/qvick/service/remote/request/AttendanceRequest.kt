package com.hs.dgsw.android.qvick.service.remote.request

import com.google.gson.annotations.SerializedName

data class AttendanceRequest (
    @field:SerializedName("code")
    val code:String
)