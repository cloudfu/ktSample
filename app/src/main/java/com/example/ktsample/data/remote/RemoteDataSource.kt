package com.example.ktsample.data.remote

import com.example.ktsample.data.api.ApiService
import com.example.ktsample.data.repository.ResultPackage
import com.example.ktsample.data.city.City
import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.repository.DataPackageState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val networkProvider: NetworkProvider
) {
    suspend fun getCities(): ResultPackage<CityList> {
        val apiService = networkProvider.createApiService(ApiService::class.java)
        return when(val result = networkProvider.request(apiService::getCities)){
            is List<*> ->{
                ResultPackage(
                    data = CityList(result as List<City>),
                    state = DataPackageState.SUCCEED
                )
            }
            // ErrCode
            is Int -> {
                ResultPackage(
                    data = null,
                    state = DataPackageState.SERVER_FAILED
                )
            }
            else -> {
                ResultPackage(
                    data = null,
                    state = DataPackageState.UNKNOWN_ERROR
                )
            }
        }
    }
}