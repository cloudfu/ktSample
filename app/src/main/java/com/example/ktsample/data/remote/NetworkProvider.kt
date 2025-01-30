package com.example.ktsample.data.remote

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProvider @Inject constructor(@ApplicationContext val context: Context){

    private var mHttpClient: OkHttpClient? = null
    private lateinit var mRetrofit: Retrofit;
    val BASE_URL = "https://jenly1314.gitlab.io/"
    val NO_INTERNET_CONNECTION_CODE = "1000"
    val NETWORK_ERROR_CODE = "1001"

    init{
        mHttpClient = getHttpClient()
        mRetrofit = Retrofit.Builder()
            .client(mHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(baseUrl: String): Retrofit{
        if(mHttpClient == null){
            mHttpClient = getHttpClient()
        }

        mRetrofit = Retrofit.Builder()
            .client(mHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return mRetrofit
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun getHttpClient(): OkHttpClient{
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)
        return builder.build()
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