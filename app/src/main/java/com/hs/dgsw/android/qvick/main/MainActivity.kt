package com.hs.dgsw.android.qvick.main

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.databinding.ActivityMainBinding
import com.hs.dgsw.android.qvick.privacy.TermsOfUseActivity
import com.hs.dgsw.android.qvick.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

     lifecycleScope.launch(Dispatchers.IO){
         kotlin.runCatching {
             RetrofitBuilder.getStudentService().getStudent()
         }.onSuccess {
             Log.d(TAG, "성공: $it")
             val intent = Intent(applicationContext, HomeActivity::class.java)
             startActivity(intent)
         }.onFailure {
             Log.d(TAG, "실패: $it")
             val intent = Intent(applicationContext, TermsOfUseActivity::class.java)
             startActivity(intent)
         }
     }

    }
}