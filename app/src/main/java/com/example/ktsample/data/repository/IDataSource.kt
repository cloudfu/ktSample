package com.example.ktsample.data.repository

import com.example.ktsample.data.city.CityList

interface IDataSource {
    suspend fun getCities(): ResultPackage<CityList>
}