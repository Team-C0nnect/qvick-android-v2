package com.hs.dgsw.android.qvick.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.databinding.ActivityHomeBinding
import com.hs.dgsw.android.qvick.login.UserDataApplication
import com.hs.dgsw.android.qvick.login.UserDataManager
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        UserDataApplication.init(this)
        UserDataManager.init(this)

        val application = UserDataApplication.getApplication()
        val name = UserDataManager.getName()
        var checkedDate = ""


        if (application == false){
            binding.checkText.setText("출석미완료")
            binding.checkText.setTextColor(Color.RED)
        } else{
            binding.checkText.setText("출석완료")
            binding.checkText.setTextColor(Color.GREEN)
        }

        Log.d("hometext", "${name}")


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