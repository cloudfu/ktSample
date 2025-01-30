package com.example.ktsample.data.repository

import androidx.collection.LruCache
import com.example.ktsample.data.city.City
import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    @Inject
    lateinit var mRetrofit: Retrofit

    fun getCities(): Flow<ResultPackage<CityList>> {
        return flow {
            emit(remoteDataSource.getCities())
        }.flowOn(Dispatchers.IO)
    }

    private val mRetrofitServiceCache: LruCache<Class<*>, Any> by lazy {
        LruCache(10)
    }

    /***
     * 获取远程数据接口服务
     */
    fun <T> getRemoteService(service: Class<T>): T {
        var retrofitService = mRetrofitServiceCache[service] as? T
        if (retrofitService == null) {
            // TODO: synchronized(mRetrofit)
            synchronized(mRetrofitServiceCache) {
                retrofitService = mRetrofit.create(service)
                mRetrofitServiceCache.put(service, retrofitService!!)
            }
        }
        return retrofitService!!
    }

    /***
     * 获取本地数据接口服务
     */
    fun getLocalService() {
        TODO("Not yet implemented")
    }

}