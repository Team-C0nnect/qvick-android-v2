package com.hs.dgsw.android.qvick.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.databinding.ActivityStudentBinding
import com.hs.dgsw.android.qvick.privacy.TermsOfUseActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.request.FirebaseRequest
import com.hs.dgsw.android.qvick.service.remote.request.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class StudentIdActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)

        binding.SignUpBtn.setOnClickListener {

            val email = UserDataManager.getEmail()
            val pass = UserDataManager.getPassword()
            val name = binding.nameEditText.text.toString()
            val student = binding.studentIdEditText.text.toString()
            val room = binding.roomNumberEditText.text.toString()

            if (name == "" || student == "" || room == ""){
                Toast.makeText(this, "회원정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                Log.d(TAG, "name: ${name} email: ${email} pass:${pass} student: ${student} room: ${room}")
                lifecycleScope.launch(Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getSignUpService().postSignUp(
                            body = SignUpRequest(
                                name = name,
                                email = email,
                                password = pass,
                                stdId = student,
                                room = room
                            )
                        )
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 성공!!: $it")
                        UserDataManager.setUserData(email, pass, name, room, student)
                        intent = Intent(applicationContext, TermsOfUseActivity::class.java)
                        startActivity(intent)
                    }.onFailure {
                        Log.d(TAG, "onCreate: 실패 : $it")
                        /**
                         * 409:이미 존재하는 유저입니다.
                         */
                        withContext(Dispatchers.IO){
                            Toast.makeText(this@StudentIdActivity, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                        }
                        intent = Intent(applicationContext, SignUpActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}