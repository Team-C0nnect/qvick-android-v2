package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.response.BaseResponse
import com.hs.dgsw.android.qvick.service.remote.response.UserResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("/user")
    suspend fun getUser(
        @Header("authorization") accessToken : String
    ):BaseResponse<UserResponse>


    @DELETE("/user")
    suspend fun deleteUser(
        @Header("authorization") accessToken : String
    ):BaseResponse<Unit>
}