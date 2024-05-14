package com.hs.dgsw.android.qvick.service.remote.request

import com.google.gson.annotations.SerializedName

data class StudentIdRequest (
    @field:SerializedName("stdId")
    val stdId:String
)