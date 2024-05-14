package com.hs.dgsw.android.qvick.service.remote.service

import com.hs.dgsw.android.qvick.service.remote.request.StudentIdRequest
import retrofit2.http.Body
import retrofit2.http.PATCH

interface StudentIdService {
    @PATCH("/user/stdId")
    suspend fun patchStudentId(
        @Body body: StudentIdRequest
    )
}