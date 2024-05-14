package com.hs.dgsw.android.qvick.menu

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.privacy.PrivacyTermsActivity
import com.hs.dgsw.android.qvick.databinding.ActivityMenuBinding
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.login.LoginActivity
import com.hs.dgsw.android.qvick.login.UserDataManager
import com.hs.dgsw.android.qvick.profile.ProfileActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.local.TokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)

        val stdId = UserDataManager.getStdId()
        val name = UserDataManager.getName()

        binding.goProfileBtn.text = stdId+name

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

        // 로그아웃
        binding.logoutBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.let { tokenEntity ->
                    QvickDataBase.getInstance(applicationContext)?.tokenDao()?.deleteMember(tokenEntity)
                }
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}