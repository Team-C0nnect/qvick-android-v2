package com.hs.dgsw.android.qvick.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.databinding.ActivityEditProfileBinding
import com.hs.dgsw.android.qvick.login.UserDataApplication
import com.hs.dgsw.android.qvick.login.UserDataManager
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.request.StudentIdRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)
        UserDataApplication.init(this)

        val name = UserDataManager.getName()
        val stdId = UserDataManager.getStdId()
        val room = UserDataManager.getRoom()
        val application = UserDataApplication.getApplication()

        // 상태 값 변경
        binding.nameEditText.setText(name)
        binding.studentIDEditText.setText(stdId)
        binding.roomNumberEditText.setText(room)
        if (application == false){
            binding.AttendanceEditText.setText("미출석")
            binding.AttendanceEditText.setTextColor(Color.RED)
        } else{
            binding.AttendanceEditText.setText("출석")
            binding.AttendanceEditText.setTextColor(Color.GREEN)
        }

        // 프로필 화면으로 이동
        binding.finishBtn.setOnClickListener {

            // 사진 추가 해야함
            var classNumberSave = binding.studentIDEditText.text.toString()

            if (classNumberSave.isEmpty()) {
                Toast.makeText(applicationContext, "학번을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {

                // 학번
                lifecycleScope.launch(Dispatchers.IO) {
                    kotlin.runCatching {
                        RetrofitBuilder.getStudentService().patchStudentId(
                            body = StudentIdRequest(
                                stdId = classNumberSave
                            )
                        )
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 성공!!: $it")
                    }.onFailure {
                        Log.d(TAG, "onCreate: 실패: $it")
                    }
                }
                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }

        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}