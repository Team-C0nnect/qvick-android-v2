package com.hs.dgsw.android.qvick.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.databinding.ActivityProfileBinding
import com.hs.dgsw.android.qvick.login.UserDataApplication
import com.hs.dgsw.android.qvick.login.UserDataManager

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)
        UserDataApplication.init(this)

        val name = UserDataManager.getName()
        val stdId = UserDataManager.getStdId()
        val room = UserDataManager.getRoom()
        val application = UserDataApplication.getApplication()

        // 상태 값 변경
        binding.nameEditText.setText(name)
        binding.studentIDEditText.setText(stdId)
        binding.roomNumberEditText.setText(room)
        if (application == false){
            binding.AttendanceEditText.setText("미출석")
            binding.AttendanceEditText.setTextColor(Color.RED)
        } else{
            binding.AttendanceEditText.setText("출석")
            binding.AttendanceEditText.setTextColor(Color.GREEN)
        }

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

//        fetchStudentData()
    }
//    private fun fetchStudentData(){
//        try {
//            // 학번 불러오기
//            lifecycleScope.launch(Dispatchers.IO){
//                val call = RetrofitBuilder.getStudentService().getStudent()
//                binding.studentIDEditText.setText(call.stdId)
//            }
//            // 기숙사 호실 불러오기
//            lifecycleScope.launch(Dispatchers.IO){
//                val call = RetrofitBuilder.getRoomService().getRoom()
//                binding.roomNumberEditText.setText(call.roomID)
//            }
//        } catch (e:Exception){
//            intent = Intent(this, EditProfileActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
}