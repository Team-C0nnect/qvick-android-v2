package com.hs.dgsw.android.qvick.privacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hs.dgsw.android.qvick.login.LoginActivity
import com.hs.dgsw.android.qvick.login.StudentIdActivity
import com.hs.dgsw.android.qvick.databinding.ActivityTermsOfUseBinding
import com.hs.dgsw.android.qvick.home.HomeActivity

class TermsOfUseActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTermsOfUseBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.termsOfServiceBtn.setOnClickListener {
            val serviceBottomSheetFragment = ServiceBottomSheetFragment()
            serviceBottomSheetFragment.show(supportFragmentManager, serviceBottomSheetFragment.tag)
        }

        binding.nextPageBtn.setOnClickListener {
            if (binding.termsOfServiceCB.isChecked &&
                binding.privacyCB.isChecked){
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "필수 항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show()
            }
        }


//        // 학번/기숙사 호실 입력 화면으로 이동
//        binding.nextPageBtn.setOnClickListener {
//            if (binding.termsOfServiceCB.isChecked &&
//                binding.privacyCB.isChecked){
//
//                intent = Intent(this, StudentIdActivity::class.java)
//                startActivity(intent)
//            } else{
//                Toast.makeText(applicationContext, "필수 항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show()
//            }
//        }
//        // 이전 버튼
//        binding.backBtn.setOnClickListener {
//            intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }

        // 전체동의 체크박스
        binding.fullAgreementCB.setOnClickListener {
            val isChecked = binding.fullAgreementCB.isChecked
            // 이용 약관 동의 체크박스와 개인정보 처리방침 동의 체크박스의 상태를 변경
            binding.termsOfServiceCB.isChecked = isChecked
            binding.privacyCB.isChecked = isChecked
        }

        binding.termsOfServiceCB.setOnClickListener {
            val termsOfServiceIsChecked = binding.termsOfServiceCB.isChecked
            val privacyIsChecked = binding.privacyCB.isChecked
            if (termsOfServiceIsChecked == true && privacyIsChecked == true){
                binding.fullAgreementCB.isChecked = true
            } else{
                binding.fullAgreementCB.isChecked = false
            }
        }

        binding.privacyCB.setOnClickListener {
            val termsOfServiceIsChecked = binding.termsOfServiceCB.isChecked
            val privacyIsChecked = binding.privacyCB.isChecked
            if (termsOfServiceIsChecked == true && privacyIsChecked == true){
                binding.fullAgreementCB.isChecked = true
            } else{
                binding.fullAgreementCB.isChecked = false
            }
        }


    }
}