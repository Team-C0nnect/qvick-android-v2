package com.hs.dgsw.android.qvick.outing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class SleepoverActivity :Fragment(){

    companion object {
        private const val TAG = "로그"

        fun newInstance(): SleepoverActivity {
            return SleepoverActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: 시작")
    }


}