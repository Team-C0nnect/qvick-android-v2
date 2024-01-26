package com.hs.dgsw.android.qvick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hs.dgsw.android.qvick.databinding.ActivityOutingMainBinding

class OutingMainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    //  바인딩
    private val binding by lazy {
       ActivityOutingMainBinding.inflate(layoutInflater)
    }

    private val TAG = "외출,외박 메인"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: main")


        binding.outingBottomNav.setOnNavigationItemSelectedListener(this)

        val sleepoverFragment = SleepoverActivity.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.outingFragment, sleepoverFragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sleep_over -> {
                val sleepoverFragment = SleepoverActivity.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.outingFragment, sleepoverFragment).commit()
            }
            R.id.outing -> {
                val outingFragment = OutingActivity.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.outingFragment, outingFragment).commit()
            }
        }
        return true
    }
}