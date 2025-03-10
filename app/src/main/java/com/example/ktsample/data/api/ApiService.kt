package com.example.ktsample.data.api

import com.example.ktsample.data.repository.ResultPackage
import com.example.ktsample.data.city.City
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.data.pokemon.PokemonResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    companion object {
        val BASE_URL = "https://jenly1314.gitlab.io/"
    }

    @Headers("Custom-Header: Value", "Another-Header: AnotherValue")
    @GET("api/city/hotCities.json")
    suspend fun getCities(): Response<List<City>>

    @GET("api/auth/login.json")
    suspend fun login(userName: String, userPwd: String): Response<LoginResponse>

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): PokemonResponse

    // 动态Head添加
//    @GET("data")
//    fun getData(@Header("Custom-Header") customHeaderValue: String): Call<Any>
}