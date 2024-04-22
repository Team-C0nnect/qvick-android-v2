package com.hs.dgsw.android.qvick.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.databinding.ActivityHomeBinding
import com.hs.dgsw.android.qvick.login.UserDataManager

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)

        val application = UserDataManager.getApplication()

        if (application == false){
            binding.checkText.setText("출석미완료")
            binding.checkText.setTextColor(Color.RED)
        } else{
            binding.checkText.setText("출석완료")
            binding.checkText.setTextColor(Color.GREEN)
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