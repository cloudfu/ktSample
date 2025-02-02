package com.example.ktsample.data.repository

import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.login.LoginRequest
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.data.login.OAuthCodeRequest
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.data.login.OAuthTokenResponse

interface IDataSource {

    /***
     * 标识获取数据的Code，LocalDataSource/RemoteDataSource
     */
    fun getCode(): String

    /***
     * 获取城市列表
     */
    suspend fun getCities(): ResultPackage<CityList>

    /***
     * 获取OAuth Token
     */
    suspend fun getOAuthToken(oAuthTokenRequest: OAuthTokenRequest): ResultPackage<OAuthTokenResponse>
}