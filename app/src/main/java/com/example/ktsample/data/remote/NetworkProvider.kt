package com.example.ktsample.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Proxy
import android.util.Log
import com.example.ktsample.data.api.OAuthApiService
import com.example.ktsample.data.login.OAuthTokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProvider @Inject constructor(@ApplicationContext val context: Context){

//    val BASE_URL = "https://jenly1314.gitlab.io/"
    val BASE_URL = "https://github.com/"
    val NO_INTERNET_CONNECTION_CODE = "1000"
    val NETWORK_ERROR_CODE = "1001"
    val CONNECT_TIMEOUT = 30   //In seconds

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private var mRetrofit: Retrofit

    private val httpLogger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            return loggingInterceptor
        }

    private var httpHeader = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    init{
//        val proxyAddress = InetSocketAddress("127.0.0.1", 7897)
//        val proxy = java.net.Proxy(java.net.Proxy.Type.HTTP, proxyAddress)

        okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS)
//        okHttpBuilder.proxy(proxy)
        okHttpBuilder.addInterceptor(httpLogger)
        okHttpBuilder.addInterceptor(httpHeader)
        okHttpBuilder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        okHttpBuilder.retryOnConnectionFailure(true)

        mRetrofit = Retrofit.Builder()
            .client(okHttpBuilder.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(FormUrlEncodedConverterFactory())
            .build()
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    /***
     * 创建Retrofit网络服务接口
     */
    fun <T> createApiService(serviceClass: Class<T>): T{
        return this.mRetrofit.create(serviceClass)
    }

    suspend fun request(requestApi: suspend ()-> Response<*>): Any?{
        if (isConnected()) {
            return try {
                val response = requestApi.invoke()
                val responseCode = response.code()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    responseCode
                }
            } catch (e: IOException) {
                NETWORK_ERROR_CODE
            }
        }
        return NO_INTERNET_CONNECTION_CODE
    }
}

class FormUrlEncodedConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type == OAuthTokenResponse::class.java) {
            return FormUrlEncodedResponseBodyConverter()
        }
        return null
    }
}

class FormUrlEncodedResponseBodyConverter : Converter<ResponseBody, OAuthTokenResponse> {
    override fun convert(value: ResponseBody): OAuthTokenResponse {
        val responseString = value.string()
        val params = responseString.split("&")
        val tokenResponse = OAuthTokenResponse("", "", "")

        if (params.size == 3) {
            for (param in params) {
                val keyValue = param.split("=")
                if(keyValue.size == 2){
                    when (keyValue[0]) {
                        "access_token" -> tokenResponse.accessToken = keyValue[1]
                        "scope" -> tokenResponse.scope = keyValue[1]
                        "token_type" -> tokenResponse.tokenType = keyValue[1]
                    }
                }
            }
        }
        Log.i("TAG", tokenResponse.toString())
        return tokenResponse
    }
}