package com.example.ktsample.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {

    private var mHttpClient: OkHttpClient? = null
    private lateinit var mRetrofit: Retrofit;

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

    private fun getHttpClient(): OkHttpClient{
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)
        return builder.build()
    }


}