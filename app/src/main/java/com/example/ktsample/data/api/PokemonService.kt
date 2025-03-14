package com.example.ktsample.data.api

import com.example.ktsample.data.city.City
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.data.pokemon.PokemonResponse
import com.example.ktsample.data.remote.GsonConverter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Tag

interface PokemonService {

    companion object {
        const val hostUri = "https://pokeapi.co/"
    }

    @Headers("Custom-Header: Value", "Another-Header: AnotherValue")
    @GET("api/city/hotCities.json")
    suspend fun getCities(): Response<List<City>>

    @GET("api/auth/login.json")
    suspend fun login(userName: String, userPwd: String): Response<LoginResponse>

    @GsonConverter
    @GET("api/v2/pokemon")
    suspend fun fetchPokemonList(
        @Tag hostUri: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): PokemonResponse

    // 动态Head添加
//    @GET("data")
//    fun getData(@Header("Custom-Header") customHeaderValue: String): Call<Any>
}