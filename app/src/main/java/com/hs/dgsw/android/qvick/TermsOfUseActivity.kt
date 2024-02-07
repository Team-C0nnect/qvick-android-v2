package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.dgsw.android.qvick.databinding.ActivityTermsOfUseBinding

class TermsOfUseActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTermsOfUseBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 학번/기숙사 호실 입력 화면으로 이동
        binding.nextPageBtn.setOnClickListener {
            intent = Intent(this, StudentIdActivity::class.java)
            startActivity(intent)
        }
        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, LoginToDAuthActivity::class.java)
            startActivity(intent)
        }
    }
}