package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hs.dgsw.android.qvick.databinding.ActivityTermsOfUseBinding

class TermsOfUseActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTermsOfUseBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 전체동의 체크박스
        binding.fullAgreementCB.setOnClickListener{
            binding.fullAgreementCB.setOnClickListener {
                if (!binding.termsOfServiceCB.isChecked) {
                    binding.termsOfServiceCB.isChecked = true
                }
                if (!binding.PrivacyCB.isChecked) {
                    binding.PrivacyCB.isChecked = true
                }
                if (!binding.PrivacyCB2.isChecked) {
                    binding.PrivacyCB2.isChecked = true
                }
            }
        }


        // 학번/기숙사 호실 입력 화면으로 이동
        binding.nextPageBtn.setOnClickListener {
            if (binding.termsOfServiceCB.isChecked &&
                binding.PrivacyCB.isChecked &&
                binding.PrivacyCB2.isChecked){

                intent = Intent(this, StudentIdActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(applicationContext, "필수 항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, LoginToDAuthActivity::class.java)
            startActivity(intent)
        }
    }
}