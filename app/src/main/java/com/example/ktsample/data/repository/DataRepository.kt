package com.example.ktsample.data.repository

import android.util.Log
import androidx.collection.LruCache
import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.local.LocalDataSource
import com.example.ktsample.data.login.LoginRequest
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.data.login.OAuthCodeRequest
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.data.login.OAuthTokenResponse
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.data.pokemon.PokemonResponse
import com.example.ktsample.data.remote.ApiResponse
import com.example.ktsample.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    private val TAG = "DataRepository"

    /***
     * 获取城市列表
     */
    fun getCities(): Flow<ResultPackage<CityList>> {
        return flow {
            emit(ResultPackage.loading())
            emit(remoteDataSource.getCities())
            emit(localDataSource.getCities())
        }.flowOn(Dispatchers.IO)
    }

    /***
     *
     */
    fun getOAuthToken(codeTokenRequest: OAuthTokenRequest): Flow<ApiResponse<OAuthTokenResponse>> {
        return flow {
            kotlinx.coroutines.delay(1000)
            emit(remoteDataSource.getOAuthToken(codeTokenRequest))
        }.onStart {
            emit(ApiResponse.Loading())
        }.onCompletion { cause ->
            if (cause != null) {
                println("flow completed exception")
            } else {
                println("onCompletion")
            }
            Timber.i("getOAuthToken completed")
        }.catch { ex ->
            println("catch exception: ${ex.message}")
        }.flowOn(Dispatchers.IO)
    }

    /***
     *
     */
    fun fetchPokemonList(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit

    ) = flow<ApiResponse<PokemonResponse>> {
        try{
            emit(remoteDataSource.fetchPokemonList(page))
        }catch (ex: Exception){
            onError(ex.message.toString())
        }
        }.onStart { onStart()
        }.onCompletion { onComplete()
        }.flowOn(Dispatchers.IO)


    fun doLogin(request: LoginRequest): Flow<LoginResponse>{
        return flow{

        }
    }

    private val mRetrofitServiceCache: LruCache<Class<*>, Any> by lazy {
        LruCache(10)
    }

    /***
     * 获取远程数据接口服务
     */
//    fun <T> getRemoteService(service: Class<T>): T {
//        var retrofitService = mRetrofitServiceCache[service] as? T
//        if (retrofitService == null) {
//            // TODO: synchronized(mRetrofit)
//            synchronized(mRetrofitServiceCache) {
//                retrofitService = mRetrofit.create(service)
//                mRetrofitServiceCache.put(service, retrofitService!!)
//            }
//        }
//        return retrofitService!!
//    }

    /***
     * 获取本地数据接口服务
     */
    fun getLocalService() {
        TODO("Not yet implemented")
    }

}