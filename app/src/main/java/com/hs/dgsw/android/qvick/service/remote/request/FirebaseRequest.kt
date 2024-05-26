package com.hs.dgsw.android.qvick.service.remote.request

import com.google.gson.annotations.SerializedName

data class FirebaseRequest(
    @field:SerializedName("fcmToken")
    val fcmToken: String
)
