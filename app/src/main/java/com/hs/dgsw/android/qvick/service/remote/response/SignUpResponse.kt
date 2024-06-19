package com.hs.dgsw.android.qvick.service.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @field:SerializedName("status")
    val status: Int,
    @field:SerializedName("message")
    val message: String
)
