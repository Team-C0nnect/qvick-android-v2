package com.hs.dgsw.android.qvick.service.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("stdId")
    val stdId: String,
    @field:SerializedName("room")
    val room: String,
    @field:SerializedName("userRole")
    val userRole: String,
    @field:SerializedName("checkedDate")
    val checkedDate: String,
    @field:SerializedName("checked")
    val checked: Boolean,
)