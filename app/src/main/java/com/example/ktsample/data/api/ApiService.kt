package com.example.ktsample.data.api

import com.example.ktsample.data.city.City
import retrofit2.http.GET

interface ApiService {

    @GET("api/city/hotCities.json")
    suspend fun getCities(): List<City>
}