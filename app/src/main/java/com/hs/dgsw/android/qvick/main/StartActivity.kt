package com.hs.dgsw.android.qvick.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hs.dgsw.android.qvick.databinding.ActivityStartBinding
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.login.LoginActivity
import com.hs.dgsw.android.qvick.service.local.QvickDataBase
import com.hs.dgsw.android.qvick.service.local.TokenDao
import com.hs.dgsw.android.qvick.service.local.TokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StartActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val accessToken = QvickDataBase.getInstance(applicationContext)?.tokenDao()?.getMembers()?.accessToken
            if (!accessToken.isNullOrEmpty()) {
                delay(1500)
                startActivity(Intent(this@StartActivity, HomeActivity::class.java))
                finish() // 현재 액티비티 종료
            } else {
                delay(1500)
                startActivity(Intent(this@StartActivity, LoginActivity::class.java))
                finish() // 현재 액티비티 종료
            }
        }
    }
}