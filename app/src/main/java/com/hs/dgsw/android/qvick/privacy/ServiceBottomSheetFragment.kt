package com.hs.dgsw.android.qvick.privacy

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.dgsw.android.qvick.R
import com.hs.dgsw.android.qvick.databinding.FragmentServiceBottomSheetBinding
import com.hs.dgsw.android.qvick.remote.RetrofitBuilder
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
        lifecycleScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                RetrofitBuilder.getUseTermsService().getUseTerms()
            }.onSuccess { response ->
                Log.d(TAG, "onCreate: 성공!!")

                val htmlData = response.toString() // HTML 데이터

                var serviceText = view.findViewById<TextView>(R.id.serviceText)
                serviceText.text = htmlData


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