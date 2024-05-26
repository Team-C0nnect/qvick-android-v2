package com.hs.dgsw.android.qvick.service.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hs.dgsw.android.qvick.service.remote.interceptor.TokenInterceptor
import com.hs.dgsw.android.qvick.service.remote.service.AttendanceService
import com.hs.dgsw.android.qvick.service.remote.service.FirebaseService
import com.hs.dgsw.android.qvick.service.remote.service.LoginService
import com.hs.dgsw.android.qvick.service.remote.service.PrivacyTermsService
import com.hs.dgsw.android.qvick.service.remote.service.SignUpService
import com.hs.dgsw.android.qvick.service.remote.service.TokenService
import com.hs.dgsw.android.qvick.service.remote.service.UseTermsService
import com.hs.dgsw.android.qvick.service.remote.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


// 서버통신 코드
class RetrofitBuilder {

    companion object {
        private var gson: Gson? = null
        private var retrofit: Retrofit? = null
        private var tokenRetrofit: Retrofit? = null
        private var loginService: LoginService? = null
        private var tokenService: TokenService? = null
        private var useTermsService: UseTermsService? = null
        private var privacyTermsService: PrivacyTermsService? = null
        private var attendanceService: AttendanceService? = null
        private var signUpService: SignUpService? = null
        private var userService: UserService? = null
        private var firebaseService: FirebaseService? = null

        @Synchronized
        fun getGson(): Gson? {
            if (gson == null) {
                gson = GsonBuilder().setLenient().create()
            }
            return gson
        }

        @Synchronized // 로그인 일회용
        fun getOhHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okhttpBuilder = OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            okhttpBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            okhttpBuilder.hostnameVerifier { hostname, session -> true }
            return okhttpBuilder.build()
        }

        @Synchronized // 로그인 일회용
        fun getTokenOhHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okhttpBuilder = OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .addInterceptor(TokenInterceptor())
            // TODO(로그인 인터셉터 작성하기)
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            okhttpBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            okhttpBuilder.hostnameVerifier { hostname, session -> true }
            return okhttpBuilder.build()
        }

        @Synchronized
        fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Url.serverUrl)
                    .client(getOhHttpClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()!!))
                    .build()
            }
            return retrofit!!
        }

        // 토큰이 필요한 서버연결
        @Synchronized
        fun getTokenRetrofit(): Retrofit {
            if (tokenRetrofit == null) {
                tokenRetrofit = Retrofit.Builder()
                    .baseUrl(Url.serverUrl)
                    .client(getTokenOhHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(getGson()!!))
                    .build()
            }
            return tokenRetrofit!!
        }

        @Synchronized
        fun getFirebase(): FirebaseService {
            if (firebaseService == null){
                firebaseService = getTokenRetrofit().create(FirebaseService::class.java)
            }
            return firebaseService!!
        }

        @Synchronized
        fun getUserService(): UserService{
            if (userService == null){
                userService = getTokenRetrofit().create(UserService::class.java)
            }
            return userService!!
        }

        @Synchronized
        fun getSignUpService(): SignUpService{
            if (signUpService == null){
                signUpService = getRetrofit().create(SignUpService::class.java)
            }
            return signUpService!!
        }
        @Synchronized
        fun getLoginService(): LoginService {
            if (loginService == null) {
                loginService = getRetrofit().create(LoginService::class.java)
            }
            return loginService!!
        }

        @Synchronized
        fun getUseTermsService(): UseTermsService {
            if (useTermsService == null) {
                useTermsService = getRetrofit().create(UseTermsService::class.java)
            }
            return useTermsService!!
        }

        @Synchronized
        fun getPrivacyTermsService(): PrivacyTermsService {
            if (privacyTermsService == null) {
                privacyTermsService = getRetrofit().create(PrivacyTermsService::class.java)
            }
            return privacyTermsService!!
        }

        @Synchronized
        fun getAttendanceRequestService(): AttendanceService {
            if (attendanceService == null) {
                attendanceService = getTokenRetrofit().create(AttendanceService::class.java)
            }
            return attendanceService!!
        }

        @Synchronized
        fun getTokenService(): TokenService {
            if (tokenService == null) {
                tokenService = getRetrofit().create(TokenService::class.java)
            }
            return tokenService!!
        }
    }
}