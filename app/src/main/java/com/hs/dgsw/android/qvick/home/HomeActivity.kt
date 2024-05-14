package com.hs.dgsw.android.qvick.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.databinding.ActivityHomeBinding
import com.hs.dgsw.android.qvick.login.UserDataApplication
import com.hs.dgsw.android.qvick.login.UserDataManager
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class HomeActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataApplication.init(this)

        val application = UserDataApplication.getApplication()

        if (application == false  ){
            binding.checkText.setText("출석미완료")
            binding.checkText.setTextColor(Color.RED)
        } else{
            binding.checkText.setText("출석완료")
            binding.checkText.setTextColor(Color.GREEN)
        }


        lifecycleScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                RetrofitBuilder.getAttendanceRequestService().getAttendance()
            }.onSuccess {
                UserDataApplication.setUserData(true)
            }.onFailure {
                UserDataApplication.setUserData(false)
            }
        }

        // 메뉴 화면으로 이동
        binding.settingBtn.setOnClickListener {
            intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        // BottomSheetDialogFragment뷰가 올라옴
        binding.qrCameraBtn.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}