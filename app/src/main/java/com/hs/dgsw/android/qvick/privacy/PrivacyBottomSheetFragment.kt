package com.hs.dgsw.android.qvick.privacy

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.dgsw.android.qvick.R
import com.hs.dgsw.android.qvick.databinding.FragmentPrivacyBottomSheetBinding
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PrivacyBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mBinding: FragmentPrivacyBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPrivacyBottomSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // html text 불러오기
        lifecycleScope.launch(Dispatchers.Main){
            kotlin.runCatching {
                RetrofitBuilder.getPrivacyTermsService().getPrivacyTerms()
            }.onSuccess {response ->
                Log.d(TAG, "onCreate: 성공!!")
                val htmlDate = response

                val webView = view.findViewById<WebView>(R.id.privacyText) // webView의 ID를 사용하여 찾기
                webView.loadDataWithBaseURL(null, htmlDate, "text/html", "utf-8", null)

            }.onFailure {
                Log.d(TAG, "onCreate: 실패")
            }
        }

        // 완료 버튼 구현
        mBinding.completeBtn.setOnClickListener {
            dismiss()
        }
    }
}