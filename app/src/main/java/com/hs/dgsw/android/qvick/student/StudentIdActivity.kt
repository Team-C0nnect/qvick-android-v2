package com.hs.dgsw.android.qvick.student

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
import com.hs.dgsw.android.qvick.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.remote.request.RoomRequest
import com.hs.dgsw.android.qvick.remote.request.StudentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentIdActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 홈 화면으로 이동
        binding.nextPageBtn.setOnClickListener {

            var classNumberSave = binding.studentID.text.toString()
            var roomNumberSave = binding.dormitoryLake.text.toString()

            // 학번 입력 확인
            if (classNumberSave.isEmpty()){
                Toast.makeText(applicationContext, "학번을 입력해 주세요", Toast.LENGTH_SHORT).show()

            // 기숙사 호실 입력 확인
            } else if (roomNumberSave.isEmpty()){
                Toast.makeText(applicationContext, "기숙사 호실을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else{

                // 학번 서버로 보내기
                lifecycleScope.launch(Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getStudentService().postStudent(
                            body = StudentRequest(
                                stdId = classNumberSave
                            )
                        )
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 성공!!: $it")
                    }.onFailure {
                        Log.d(TAG, "onCreate: 실패 : $it")
                    }
                }

                // 기숙사 호실 서버로 보내기
                lifecycleScope.launch (Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getRoomService().postRoom(
                            body = RoomRequest(
                                roomId = roomNumberSave
                            )
                        )
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 성공!!: $it")
                    }.onFailure {
                        Log.d(TAG, "onCreate: 실패 : $it")
                    }
                }


                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, TermsOfUseActivity::class.java)
            startActivity(intent)
        }
    }
}