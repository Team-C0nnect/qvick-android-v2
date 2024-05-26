package com.hs.dgsw.android.qvick.home

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.databinding.ActivityHomeBinding
import com.hs.dgsw.android.qvick.login.UserDataApplication
import com.hs.dgsw.android.qvick.login.UserDataManager
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.request.AttendanceRequest
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        UserDataApplication.init(this)
        UserDataManager.init(this)

        val application = UserDataApplication.getApplication()
        val name = UserDataManager.getName()


        if (application == false  ){
            binding.checkText.setText("출석미완료")
            binding.checkText.setTextColor(Color.RED)
        } else{
            binding.checkText.setText("출석완료")
            binding.checkText.setTextColor(Color.GREEN)
        }

        Log.d("hometext", "${name}")


        lifecycleScope.launch(Dispatchers.IO){
            val accessToken = QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.accessToken.toString()

            kotlin.runCatching {
                RetrofitBuilder.getAttendanceRequestService().getAttendance(accessToken)
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
            cameraService()
        }
    }
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val REQUIRED_PERMISSIONS = android.Manifest.permission.CAMERA
    }
    private fun cameraService(){
        if (allPermissionsGranted()) {
            Log.d(ContentValues.TAG, "onCreate: 성공1")
            val bottomSheetFragment = BottomSheetFragment() { text ->
                lifecycleScope.launch(Dispatchers.IO){
                    val accessToken = QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.accessToken.toString()
                    kotlin.runCatching {
                        RetrofitBuilder.getAttendanceRequestService().postAttendance(
                            accessToken = accessToken,
                            body = AttendanceRequest(
                                code = text
                            )
                        )
                    }.onSuccess {
                        Log.d(ContentValues.TAG, "성공 ㅇㅇㅇㅇㅇㅇ: $it")


                    }.onFailure {
                        it.printStackTrace()
                        Log.d(ContentValues.TAG, "실패: $it")
                    }
                }
            }
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

        } else {
            Log.d(ContentValues.TAG, "onCreate: 실패1")
            ActivityCompat.requestPermissions(
                this, arrayOf(REQUIRED_PERMISSIONS),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }
    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this, android.Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}