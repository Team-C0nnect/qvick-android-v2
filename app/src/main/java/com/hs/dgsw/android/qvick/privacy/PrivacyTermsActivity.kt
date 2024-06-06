package com.hs.dgsw.android.qvick.privacy

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.lifecycle.lifecycleScope
import com.hs.dgsw.android.qvick.R
import com.hs.dgsw.android.qvick.databinding.ActivityPrivacyTermsBinding
import com.hs.dgsw.android.qvick.home.HomeActivity
import com.hs.dgsw.android.qvick.menu.MenuActivity
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrivacyTermsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPrivacyTermsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // html text 불러오기
        lifecycleScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                RetrofitBuilder.getPrivacyTermsService().getPrivacyTerms()
            }.onSuccess { response ->
                val htmlData = response // 이미 문자열로 된 HTML 데이터

                // WebView를 사용하여 HTML 표시
                val webView = binding.serviceText
                webView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null)
            }.onFailure { exception ->
                exception.printStackTrace()
                Log.d(ContentValues.TAG, "onViewCreated: 실패")
            }
        }

        // 홈 화면으로 이동
        binding.homeBtn.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}