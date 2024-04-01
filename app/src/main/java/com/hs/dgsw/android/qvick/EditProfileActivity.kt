package com.hs.dgsw.android.qvick

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.databinding.ActivityEditProfileBinding
import com.hs.dgsw.android.qvick.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.remote.request.RoomRequest
import com.hs.dgsw.android.qvick.remote.request.StudentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    lateinit var ivProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 프로필 화면으로 이동
        binding.finishBtn.setOnClickListener {


            // 사진 추가 해야함
            var userNameSave = binding.nameEditText.text.toString()
            var classNumberSave = binding.studentIDEditText.text.toString()
            var roomNumberSave = binding.roomNumberEditText.text.toString()

            if (userNameSave.isEmpty()){
                Toast.makeText(applicationContext, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (classNumberSave.isEmpty()){
                Toast.makeText(applicationContext, "학번을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (roomNumberSave.isEmpty()){
                Toast.makeText(applicationContext, "기숙사 호실을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                // 사진필요?
                // 이름필요?

                // 학번
                lifecycleScope.launch (Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getStudentService().putStudent(
                            body = StudentRequest(
                                stdId = classNumberSave
                            )
                        )
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 성공!!: $it")
                    }.onFailure {
                        Log.d(TAG, "onCreate: 실패: $it")
                    }
                }

                // 기숙사 호실
                lifecycleScope.launch (Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getRoomService().putRoom(
                            body = RoomRequest(
                                roomId = roomNumberSave
                            )
                        )
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 성공!!: $it")
                    }.onFailure {
                        Log.d(TAG, "onCreate: 실패: $it")
                    }
                }

                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }

        // 이전 버튼
        binding.backBtn.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        binding.ivProfile.setOnClickListener{
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    navigateGallery()
                }

                // 갤러리 접근 권한이 없는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }
        }

//        fetchStudentData()
    }

//    private fun fetchStudentData(){
//        // 학번 불러오기
//        lifecycleScope.launch(Dispatchers.IO){
//            val call = RetrofitBuilder.getStudentService().getStudent()
//            binding.studentIDEditText.setText(call.stdId)
//        }
//        // 기숙사 호실 불러오기
//        lifecycleScope.launch(Dispatchers.IO){
//            val call = RetrofitBuilder.getRoomService().getRoom()
//            binding.roomNumberEditText.setText(call.roomID)
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    navigateGallery()
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
    }

    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return
        when (requestCode) {
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null){
                    ivProfile.setImageURI(selectedImageUri)
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup(){
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") {  _, _ ->  }
            .create()
            .show()
    }
}