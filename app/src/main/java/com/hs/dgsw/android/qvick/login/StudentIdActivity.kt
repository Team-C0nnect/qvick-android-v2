package com.hs.dgsw.android.qvick.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.databinding.ActivityStudentBinding
import com.hs.dgsw.android.qvick.privacy.TermsOfUseActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.request.RoomRequest
import com.hs.dgsw.android.qvick.service.remote.request.SignUpRequest
import com.hs.dgsw.android.qvick.service.remote.request.StudentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.internal.Intrinsics.Kotlin

class StudentIdActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.SignUpBtn.setOnClickListener {

            val name = binding.nameEditText.text.toString()
            val student = binding.studentIdEditText.text.toString()
            val room = binding.roomNumberEditText.text.toString()

            if (name == "" || student == "" || room == ""){
                Toast.makeText(this, "회원정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                lifecycleScope.launch(Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getSignUpService().postSignUp(
                            body = SignUpRequest(
                                // 받아야함
                                name = name,
                                email = "",
                                password = "",
                                stdId = student,
                                room = room
                            )
                        )
                    }.onSuccess {
                        intent = Intent(applicationContext, TermsOfUseActivity::class.java)
                        startActivity(intent)
                    }.onFailure {
                        Toast.makeText(this@StudentIdActivity, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, SignUpActivity::class.java)
                        startActivity(intent)
                    }
                }
            }



        }


        // 홈 화면으로 이동
//        binding.SignUpBtn.setOnClickListener {
//
//            var classNumberSave = binding.studentID.text.toString()
//            var roomNumberSave = binding.dormitoryLake.text.toString()
//
//            // 학번 입력 확인
//            if (classNumberSave.isEmpty()){
//                Toast.makeText(applicationContext, "학번을 입력해 주세요", Toast.LENGTH_SHORT).show()
//
//            // 기숙사 호실 입력 확인
//            } else if (roomNumberSave.isEmpty()){
//                Toast.makeText(applicationContext, "기숙사 호실을 입력해 주세요", Toast.LENGTH_SHORT).show()
//            } else{
//
//                // 학번 서버로 보내기
//                lifecycleScope.launch(Dispatchers.IO){
//                    kotlin.runCatching {
//                        RetrofitBuilder.getStudentService().postStudent(
//                            body = StudentRequest(
//                                stdId = classNumberSave
//                            )
//                        )
//                    }.onSuccess {
//                        Log.d(TAG, "onCreate: 성공!!: $it")
//                    }.onFailure {
//                        Log.d(TAG, "onCreate: 실패 : $it")
//                    }
//                }
//
//                // 기숙사 호실 서버로 보내기
//                lifecycleScope.launch (Dispatchers.IO){
//                    kotlin.runCatching {
//                        RetrofitBuilder.getRoomService().postRoom(
//                            body = RoomRequest(
//                                roomId = roomNumberSave
//                            )
//                        )
//                    }.onSuccess {
//                        Log.d(TAG, "onCreate: 성공!!: $it")
//                    }.onFailure {
//                        Log.d(TAG, "onCreate: 실패 : $it")
//                    }
//                }
//
//
//                intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//            }
//        }
//
//        // 이전 버튼
//        binding.backBtn.setOnClickListener {
//            intent = Intent(this, TermsOfUseActivity::class.java)
//            startActivity(intent)
//        }
    }
}