package com.hs.dgsw.android.qvick.privacy

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.dgsw.android.qvick.R
import com.hs.dgsw.android.qvick.databinding.FragmentServiceBottomSheetBinding
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ServiceBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mBinding: FragmentServiceBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentServiceBottomSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // html text 불러오기
        lifecycleScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                RetrofitBuilder.getUseTermsService().getUseTerms()
            }.onSuccess { response ->
                val htmlData = response // 이미 문자열로 된 HTML 데이터

                // WebView를 사용하여 HTML 표시
                val webView = view.findViewById<WebView>(R.id.serviceText)
                webView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null)
            }.onFailure { exception ->
                exception.printStackTrace()
                Log.d(TAG, "onViewCreated: 실패")
            }
        }


        // 완료 버튼 구현
        mBinding.completeBtn.setOnClickListener {
            dismiss()
        }
    }
}
