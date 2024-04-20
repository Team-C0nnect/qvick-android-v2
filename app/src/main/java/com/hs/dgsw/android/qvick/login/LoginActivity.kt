package com.hs.dgsw.android.qvick.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hs.dgsw.android.qvick.databinding.ActivityLoginBinding
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.main.MainActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.local.TokenEntity
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.Utils
import com.hs.dgsw.android.qvick.service.remote.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {



    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 회원가입
        binding.GoSignUpBtn.setOnClickListener{
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 로그인
        binding.LogoInBtn.setOnClickListener {
            val user = binding.emailEditText!!.text.toString()
            val pass = binding.passwordEditText!!.text.toString()

            if (user == "" || pass == ""){
                Toast.makeText(this, "회원정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                lifecycleScope.launch(Dispatchers.IO){
                    kotlin.runCatching {
                        RetrofitBuilder.getLoginService().postLogin(
                             body = LoginRequest(
                                 email = user,
                                 password = pass
                             )
                        )
                    }.onSuccess {
                        lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "로그인 되었습니다", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }.onFailure {
                        lifecycleScope.launch(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "회원정보 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}