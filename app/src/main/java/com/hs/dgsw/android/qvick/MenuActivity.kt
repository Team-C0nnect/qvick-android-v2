package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.dgsw.android.qvick.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 프로필 화면으로 이동
        binding.goProfileBtn.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        // 외출/외박 화면으로 이동
//        binding.goOutingBtn.setOnClickListener {
//            intent = Intent(this, OutingMainActivity::class.java)
//            startActivity(intent)
//        }
        // 개인정보 이용약관 뷰로 이동
        binding.goPrivacyBtn.setOnClickListener {
            intent = Intent(this, PrivacyTermsActivity::class.java)
            startActivity(intent)
        }

        // 나가기 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}