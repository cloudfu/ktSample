package com.example.ktsample.data.api

import com.example.ktsample.data.repository.ResultPackage
import com.example.ktsample.data.city.City
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    companion object {
        val DOMAIN = "https://jenly1314.gitlab.io/"
    }

    @Headers("Custom-Header: Value", "Another-Header: AnotherValue")
    @GET("api/city/hotCities.json")
    suspend fun getCities(): Response<List<City>>

    // 动态Head添加
//    @GET("data")
//    fun getData(@Header("Custom-Header") customHeaderValue: String): Call<Any>
}