package com.hs.dgsw.android.qvick.service.remote

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hs.dgsw.android.qvick.service.remote.interceptor.TokenInterceptor
import com.hs.dgsw.android.qvick.service.remote.service.AttendanceService
import com.hs.dgsw.android.qvick.service.remote.service.LoginService
import com.hs.dgsw.android.qvick.service.remote.service.PrivacyTermsService
import com.hs.dgsw.android.qvick.service.remote.service.RoomService
import com.hs.dgsw.android.qvick.service.remote.service.StudentService
import com.hs.dgsw.android.qvick.service.remote.service.TokenService
import com.hs.dgsw.android.qvick.service.remote.service.UseTermsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
        private var studentService: StudentService? = null
        private var roomService: RoomService? = null
        private var useTermsService: UseTermsService? = null
        private var privacyTermsService: PrivacyTermsService? = null
        private var attendanceService: AttendanceService? = null

        @Synchronized
        fun getGson(): Gson? {
            if (gson == null) {
                gson = GsonBuilder().create()
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
                    .baseUrl("http://13.125.244.155:8080")
                    .addConverterFactory(GsonConverterFactory.create(getGson()!!))
                    .build()
            }
            return retrofit!!
        }

        @Synchronized
        fun getTokenRetrofit(): Retrofit {
            if (tokenRetrofit == null) {
                tokenRetrofit = Retrofit.Builder()
                    .baseUrl("http://13.125.244.155:8080")
                    .addConverterFactory(GsonConverterFactory.create(getGson()!!))
                    .build()
            }
            return tokenRetrofit!!
        }

        @Synchronized
        fun getLoginService(): LoginService {
            if (loginService == null) {
                loginService = getRetrofit().create(LoginService::class.java)
            }
            return loginService!!
        }

        @Synchronized
        fun getStudentService(): StudentService {
            if (studentService == null) {
                studentService = getRetrofit().create(StudentService::class.java)
            }
            return studentService!!
        }

        @Synchronized
        fun getRoomService(): RoomService {
            if (roomService == null) {
                roomService = getRetrofit().create(RoomService::class.java)
            }
            return roomService!!
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
                attendanceService = getRetrofit().create(AttendanceService::class.java)
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