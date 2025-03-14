package com.example.ktsample.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.ktsample.data.login.OAuthTokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProvider @Inject constructor(@ApplicationContext val context: Context){

//    val BASE_URL = "https://jenly1314.gitlab.io"
    val BASE_URL = "https://github.com/"
//    val BASE_URL = "https://pokeapi.co/api/v2/"


    val NO_INTERNET_CONNECTION_CODE = "1000"
    val NETWORK_ERROR_CODE = "1001"
    val CONNECT_TIMEOUT = 30   //In seconds

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private var mRetrofit: Retrofit

    // 日志打印
    private val httpLogger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            return loggingInterceptor
        }

    // 增加Http Head
    private var httpHeader = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    // 动态HostUri
    private var dynamicHostUri = Interceptor { chain ->
        var request = chain.request()
        val newHostUri = request.tag(String::class.java)?.toHttpUrlOrNull()

        // update scheme & host
        newHostUri?.let {
            val builder = request.url.newBuilder()
                .scheme(it.scheme)
                .host(it.host)
                .build()

            // update request
            request = request.newBuilder()
                .url(builder)
                .build()
        }

        /***
         *         val originalRequest = chain.request()
         *         val newBaseUrl = originalRequest.tag(String::class.java)
         *         if (!newBaseUrl.isNullOrEmpty()) {
         *
         *             val newUrl = originalRequest.url.newBuilder()
         *                 .scheme(newBaseUrl.split("://")[0])
         *                 .host(newBaseUrl.split("://")[1].split("/")[0])
         *                 .build()
         *
         *             val newRequest: Request = originalRequest.newBuilder()
         *                 .url(newUrl)
         *                 .build()
         *
         *             chain.proceed(newRequest)
         *         }else {
         *             chain.proceed(originalRequest)
         *         }
         */

        chain.proceed(request)
    }

    init{
        // Proxy
//        val proxyAddress = InetSocketAddress("127.0.0.1", 7897)
//        val proxy = java.net.Proxy(java.net.Proxy.Type.HTTP, proxyAddress)
//        okHttpBuilder.proxy(proxy)

        okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS)
        okHttpBuilder.addInterceptor(httpLogger)
        okHttpBuilder.addInterceptor(httpHeader)
        okHttpBuilder.addInterceptor(dynamicHostUri)
        okHttpBuilder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        okHttpBuilder.retryOnConnectionFailure(true)

        mRetrofit = Retrofit.Builder()
            .client(okHttpBuilder.build())
            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .addConverterFactory(FormUrlEncodedConverterFactory())
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(DynamicConverterFactory())
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

// 定义 XML 注解
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FormConverter

// 定义 JSON 注解
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GsonConverter

class DynamicConverterFactory(
) : Converter.Factory() {

    private val formConverterFactory = FormConverterFactory()
    private val jsonConverterFactory = GsonConverterFactory.create()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        annotations.forEach { annotation ->
            when (annotation) {
                is FormConverter -> return formConverterFactory.responseBodyConverter(type, annotations, retrofit)
                is GsonConverter -> return jsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
            }
        }
        return null
    }

    override fun requestBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        annotations.forEach { annotation ->
            when (annotation) {
                is FormConverter -> return formConverterFactory.requestBodyConverter(type, annotations, methodAnnotations, retrofit)
                is GsonConverter -> return jsonConverterFactory.requestBodyConverter(type, annotations, methodAnnotations, retrofit)
            }
        }
        return null
    }
}

class FormConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return FormUrlEncodedResponseBodyConverter()
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
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
        Timber.i(tokenResponse.toString())
        return tokenResponse
    }
}