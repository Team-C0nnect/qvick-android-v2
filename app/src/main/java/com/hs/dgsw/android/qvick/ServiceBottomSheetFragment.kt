package com.hs.dgsw.android.qvick

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.dgsw.android.qvick.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log


class ServiceBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
        val completeBtn = view.findViewById<AppCompatButton>(R.id.completeBtn)
        completeBtn.setOnClickListener {
            dismiss()
        }
    }
}