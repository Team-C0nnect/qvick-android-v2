package com.hs.dgsw.android.qvick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hs.dgsw.android.qvick.databinding.ActivityLoginToDauthBinding

class LoginToDAuthActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginToDauthBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}