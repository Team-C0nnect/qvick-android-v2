package com.hs.dgsw.android.qvick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hs.dgsw.android.qvick.databinding.ActivityLoginToDauthBinding
import com.hs.dgsw.android.qvick.local.QvickDataBase
import com.hs.dgsw.android.qvick.local.TokenEntity
import com.hs.dgsw.android.qvick.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.remote.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginToDAuthActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginToDauthBinding.inflate(layoutInflater)
    }


    // 구글로 로그인 코드
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher =
        this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("TAG", ": ${result.data}")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                val token = task.result
            })
            try {
                val account = task.getResult(ApiException::class.java)

                val userName = account.givenName
                val serverAuth = account.serverAuthCode
                val idToken = account.idToken ?: ""
                var fcmToken = ""

                lifecycleScope.launch (Dispatchers.IO) {
                    runBlocking {
                        fcmToken = QvickDataBase.getInstance(applicationContext)?.fcmTokenDao()?.getMembers()?.fcmToken.toString()
                    }

                    RetrofitBuilder.getLoginService().login(
                        LoginRequest(
                            idToken = idToken,
                            fcmToken = fcmToken
                        )
                    ).let { result ->

                        QvickDataBase.getInstance(applicationContext)?.tokenDao()?.insertMember(
                            TokenEntity(
                                accessToken = result.accessToken,
                                refreshToken = result.refreshToken
                            )
                        ).let {
                            // 서버에서 정보가 있으면 메인으로 가고 없으면 회원가입 하고 서버로e 보내기
                            Log.d("TAG", ": 성공했다잉 아잉")

                            lifecycleScope.launch(Dispatchers.Main) {
                                val intent =
                                    Intent(applicationContext, TermsOfUseActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(LoginToDAuthActivity::class.java.simpleName, e.stackTraceToString())
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initButton()
    }


    private fun getGoogleClient():GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("246755219900-ubbuhsmojfign0du3pulukfi6a3843fv.apps.googleusercontent.com")
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    override fun onStart() {
        super.onStart()
// 사용자가 이미 로그인한 경우 기존 Google 로그인 계정을 확인합니다.
// GoogleSignInAccount는 null이 아닙니다.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Log.d("TAG", "onStart: ${account?.idToken}")
//        updateUI(account);

    }


    private fun initButton() {
        with(binding) {
            googleLoginBtn.setOnClickListener {
                requestGoogleLogin()
            }
        }
    }
}


