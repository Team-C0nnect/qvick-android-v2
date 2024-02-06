package com.hs.dgsw.android.qvick.remote.request

import com.google.gson.annotations.SerializedName

data class TokenRequest (
    @field:SerializedName("refreshToken")
    val refreshToken: String
)