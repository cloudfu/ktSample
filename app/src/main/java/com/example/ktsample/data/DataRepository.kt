package com.example.ktsample.data

import androidx.collection.LruCache
import com.example.ktsample.data.login.LoginRequest
import com.example.ktsample.data.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DataRepository : DataRepositoryImpl{
    private var BASE_URL: String = "https://jenly1314.gitlab.io/"

    private val mRetrofitServiceCache: LruCache<Class<*>, Any> by lazy {
        LruCache(10)
    }
    private var mRetrofit: Retrofit? = null


    // TODO: synchronized, retrofitService!!
    override fun <T> getRemoteService(service: Class<T>): T {
        var retrofitService = mRetrofitServiceCache[service] as? T
        if (retrofitService == null) {
            // TODO: synchronized(mRetrofit)
            synchronized(mRetrofitServiceCache) {
                if(mRetrofit == null){
                    mRetrofit = RetrofitService().getInstance(BASE_URL)
                }
                retrofitService = mRetrofit?.create(service)
                mRetrofitServiceCache.put(service, retrofitService!!)
            }
        }
        return retrofitService!!
    }
}