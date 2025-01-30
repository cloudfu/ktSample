package com.example.ktsample.data.api

import com.example.ktsample.data.repository.ResultPackage
import com.example.ktsample.data.city.City
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    companion object {
        val DOMAIN = "https://jenly1314.gitlab.io/"
    }

    @GET("api/city/hotCities.json")
    suspend fun getCities(): Response<List<City>>
}