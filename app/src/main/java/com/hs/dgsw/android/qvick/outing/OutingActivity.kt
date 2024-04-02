package com.hs.dgsw.android.qvick.outing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class OutingActivity : Fragment() {
    companion object {
        private const val TAG = "로그"

        fun newInstance(): OutingActivity {
            return OutingActivity()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: 시작2")
    }
}