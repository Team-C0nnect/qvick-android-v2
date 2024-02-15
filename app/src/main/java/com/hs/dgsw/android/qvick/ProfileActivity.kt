package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.dgsw.android.qvick.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 프로필 수정 화면으로 이동
        binding.editBtn.setOnClickListener {
            intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}