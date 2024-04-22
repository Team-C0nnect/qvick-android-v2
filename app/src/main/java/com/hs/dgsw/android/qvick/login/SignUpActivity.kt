package com.hs.dgsw.android.qvick.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hs.dgsw.android.qvick.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.SignUpBtn.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            val repass = binding.repasswordEditText.text.toString()

            if (email == "" || pass == "" || repass == ""){
                Toast.makeText(this, "회원정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                if (pass == repass){
                    // email, pass를 디비에 저장
                    UserDataManager.setUserData(email, pass)
                    Log.d(TAG, "onCreate: 성공!!: $it")
                    val intent = Intent(this, StudentIdActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}