package com.hs.dgsw.android.qvick.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hs.dgsw.android.qvick.databinding.ActivityStartBinding
import com.hs.dgsw.android.qvick.login.LoginActivity

class StartActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 1500)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}