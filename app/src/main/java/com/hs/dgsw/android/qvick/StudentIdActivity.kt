package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, TermsOfUseActivity::class.java)
            startActivity(intent)
        }
    }
}