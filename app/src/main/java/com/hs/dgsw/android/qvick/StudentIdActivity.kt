package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.hs.dgsw.android.qvick.databinding.ActivityStudentBinding

class StudentIdActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStudentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 홈 화면으로 이동
        binding.nextPageBtn.setOnClickListener {
            // 학번 입력 확인
            if (binding.studentID.text.toString().trim().isEmpty()){
                Toast.makeText(this, "학번을 입력해 주세요", Toast.LENGTH_SHORT).show()
            // 기숙사 호실 입력 확인
            } else if (binding.dormitoryLake.text.toString().trim().isEmpty()){
                Toast.makeText(this, "기숙사 호실을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else{
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