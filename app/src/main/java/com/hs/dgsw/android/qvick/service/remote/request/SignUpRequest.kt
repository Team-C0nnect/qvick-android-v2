package com.hs.dgsw.android.qvick.service.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest (
    @field:SerializedName("name")
    val name:String,
    @field:SerializedName("email")
    val email:String,
    @field:SerializedName("password")
    val password:String,
    @field:SerializedName("stdId")
    val stdId:String,
    @field:SerializedName("room")
    val room:String
)