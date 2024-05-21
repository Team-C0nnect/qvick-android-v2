package com.hs.dgsw.android.qvick.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.hs.dgsw.android.qvick.R
import com.hs.dgsw.android.qvick.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private var isVisible = false
    private var isVisibleCheck = false
    init {
        isVisible = false
        isVisibleCheck = false
    }

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserDataManager.init(this)

        binding.SignUpBtn.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            val repass = binding.repasswordEditText.text.toString()

            if (email == "" || pass == "" || repass == ""){
                Toast.makeText(this, "회원정보를 전부 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                if (pass == repass){
                    // email, pass를 디비에 저장
                    UserDataManager.setUserData(email, pass, "", "", "")
                    Log.d(TAG, "onCreate: 성공!!: $it")
                    val intent = Intent(this, StudentIdActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.showBtn.setOnClickListener {
            changeShowBtn()
        }
        binding.showBtn2.setOnClickListener {
            changeShowCheckBtn()
        }
    }
    fun changeShowBtn(){
        var lastIndex : Int = 0
        if (binding.passwordEditText.text?.lastIndex != null){
            lastIndex = binding.passwordEditText.text?.lastIndex!!+1
        }

        isVisible = if(isVisible){
            binding.showBtn.setBackgroundResource(R.drawable.ic_offeye)
            binding.passwordEditText.inputType = 0x00000081
            binding.passwordEditText.setSelection(lastIndex)
            false
        } else{
            binding.showBtn.setBackgroundResource(R.drawable.ic_oneye)
            binding.passwordEditText.inputType = 0x00000091
            binding.passwordEditText.setSelection(lastIndex)
            true
        }
    }
    fun changeShowCheckBtn(){
        var lastIndex : Int = 0
        if (binding.repasswordEditText.text?.lastIndex != null){
            lastIndex = binding.repasswordEditText.text?.lastIndex!!+1
        }

        isVisibleCheck = if(isVisibleCheck){
            binding.showBtn2.setBackgroundResource(R.drawable.ic_offeye)
            binding.repasswordEditText.inputType = 0x00000081
            binding.repasswordEditText.setSelection(lastIndex)
            false
        } else{
            binding.showBtn2.setBackgroundResource(R.drawable.ic_oneye)
            binding.repasswordEditText.inputType = 0x00000091
            binding.repasswordEditText.setSelection(lastIndex)
            true
        }
    }
}