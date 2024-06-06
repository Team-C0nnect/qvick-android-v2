package com.hs.dgsw.android.qvick.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hs.dgsw.android.qvick.R
import com.hs.dgsw.android.qvick.databinding.ActivityLoginBinding
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.local.TokenDao
import com.hs.dgsw.android.qvick.service.local.TokenEntity
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.request.FirebaseRequest
import com.hs.dgsw.android.qvick.service.remote.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    private var isVisible = false

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    init {
        isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test", "destroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
//                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })


        // 회원가입
        binding.GoSignUpBtn.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.showBtn.setOnClickListener {
            changeShowBtn()
        }

        // 로그인
        binding.LogoInBtn.setOnClickListener {
            val user = binding.emailEditText!!.text.toString()
            val pass = binding.passwordEditText!!.text.toString()

            if (user == "" || pass == "") {
                Toast.makeText(this, "회원정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    kotlin.runCatching {
                        RetrofitBuilder.getLoginService().postLogin(
                            body = LoginRequest(
                                email = user,
                                password = pass
                            )
                        ).let {result ->
                            QvickDataBase.getInstance(applicationContext)?.tokenDao()?.insertMember(
                                TokenEntity(
                                    accessToken = result.accessToken,
                                    refreshToken = result.refreshToken
                                )
                            )
                        }
                    }.onSuccess {
                        Log.d(TAG, "onCreate: 로그인 성공")

                        service()
//                        lifecycleScope.launch(Dispatchers.Main) {
//                            Toast.makeText(this@LoginActivity, "로그인 되었습니다", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(applicationContext, HomeActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
                    }.onFailure {
                        it.printStackTrace()
                        Log.d(TAG, "onCreate: 로그인 실패")
                        lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "회원정보 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }



    private fun service() {

        val accessToken = QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.accessToken.toString()
//        val fcmToken = QvickDataBase.getInstance(applicationContext)?.fcmTokenDao()?.getMembers()?.fcmToken.toString()

//        if (fcmToken != null){
//            lifecycleScope.launch(Dispatchers.IO){
//                Log.d(TAG, "service: ${fcmToken}")
//                kotlin.runCatching {
//                    RetrofitBuilder.getFirebase().postFcm(
//                        accessToken = accessToken,
//                        body = FirebaseRequest(
//                            fcmToken = fcmToken
//                        )
//                    )
//                }.onSuccess {
//                    Log.d(TAG, "service: fcm성공")
//                }.onFailure {
//                    it.printStackTrace()
//                    Log.d(TAG, "service: fcm실패")
//                }
//            }
//        }

        if (accessToken != null){
            lifecycleScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    RetrofitBuilder.getUserService().getUser(accessToken)
                }.onSuccess { userResponse ->
                    // 사용자 정보를 가져와서 UserDataManager를 통해 저장합니다.
                    userResponse?.let {
                        UserDataManager.setUserData(email = it.email, password = it.password, name = it.name, room = it.room, stdId = it.stdId)
                        Log.d(TAG, "service 성공: ${UserDataManager.getName()}")
                    }
                }.onFailure {
                    it.printStackTrace()
                    Log.d(TAG, "service: 실패")
                }
            }
        }


        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@LoginActivity, "로그인 되었습니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun changeShowBtn(){
        var lastIndex : Int = 0
        if (binding.passwordEditText.text?.lastIndex != null){
            lastIndex = binding.passwordEditText.text?.lastIndex!!+1
        }

        isVisible = if(isVisible){
            binding.showBtn.setBackgroundResource(R.drawable.ic_oneye)
            binding.passwordEditText.inputType = 0x00000081
            binding.passwordEditText.setSelection(lastIndex)
            false
        } else{
            binding.showBtn.setBackgroundResource(R.drawable.ic_offeye)
            binding.passwordEditText.inputType = 0x00000091
            binding.passwordEditText.setSelection(lastIndex)
            true
        }
    }
}