package com.hs.dgsw.android.qvick.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.databinding.ActivityProfileBinding
import com.hs.dgsw.android.qvick.login.UserDataManager
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        kotlin.runCatching {
            getUserData()
        }


        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

//        fetchStudentData()
    }

    private fun getUserData() {
        lifecycleScope.launch(Dispatchers.IO){
            val accessToken = QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.accessToken.toString()
            kotlin.runCatching {
                RetrofitBuilder.getUserService().getUser(accessToken)
            }.onSuccess { value ->
                binding.nameEditText.setText(value.data.name)
                binding.studentIDEditText.setText(value.data.stdId)
                binding.roomNumberEditText.setText(value.data.room)

                if (value.data.checked){
                    binding.AttendanceEditText.setText("출석")
                    binding.AttendanceEditText.setTextColor(Color.GREEN)
                } else{
                    binding.AttendanceEditText.setText("미출석")
                    binding.AttendanceEditText.setTextColor(Color.RED)
                }
            }
        }
    }
}