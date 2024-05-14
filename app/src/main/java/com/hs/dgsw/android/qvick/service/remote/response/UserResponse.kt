package com.hs.dgsw.android.qvick.service.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("phoneNum")
    val phoneNum: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("stdId")
    val stdId: String,
    @field:SerializedName("room")
    val room: String,
    @field:SerializedName("approval")
    val approval: String,
    @field:SerializedName("userRole")
    val userRole: String,
)