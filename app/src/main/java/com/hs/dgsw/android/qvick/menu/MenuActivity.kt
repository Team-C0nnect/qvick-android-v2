package com.hs.dgsw.android.qvick.menu

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hs.dgsw.android.qvick.privacy.PrivacyTermsActivity
import com.hs.dgsw.android.qvick.databinding.ActivityMenuBinding
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.login.LoginActivity
import com.hs.dgsw.android.qvick.privacy.UseTermsActivity
import com.hs.dgsw.android.qvick.profile.ProfileActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        kotlin.runCatching {
            getUserData()
        }


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

        binding.goUseBtn.setOnClickListener {
            intent = Intent(this, UseTermsActivity::class.java)
            startActivity(intent)
        }

        // 나가기 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // 회원 탈퇴
        binding.quitBtn.setOnClickListener {
            quit()
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

    private fun getUserData() {
        Log.d(TAG, "getUserData: 됬으여")


        lifecycleScope.launch(Dispatchers.IO){
            val accessToken = QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.accessToken.toString()
            kotlin.runCatching {
                RetrofitBuilder.getUserService().getUser(accessToken)
            }.onSuccess { value ->
                binding.goProfileBtn.setText(value.data.stdId+" "+value.data.name)
                Log.d(TAG, "getUserData: ${value.data.stdId+""+value.data.name}")
            }.onFailure { 
                it.printStackTrace()
                Log.d(TAG, "getUserData: 실패")
            }
        }
    }

    fun quit(){
        MaterialAlertDialogBuilder(this)
            .setMessage("정말로 탈퇴하시겠습니까?")
            .setNegativeButton("취소") { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton("탈퇴") { dialog, which ->
                // Respond to positive button press
                val dao = QvickDataBase.getInstanceOrNull()?: throw RuntimeException()
                val tokenDao = dao.tokenDao()
                val accessToken = tokenDao.getMembers().accessToken
                lifecycleScope.launch(Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getUserService().deleteUser(accessToken)
                    }.onSuccess {
                        finish()
                    }.onFailure {
                        Toast.makeText(this@MenuActivity, "계정 탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show()

                    }
                }
            }.show()
    }
}