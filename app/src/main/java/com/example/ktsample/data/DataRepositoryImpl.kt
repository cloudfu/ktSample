package com.example.ktsample.data

import com.example.ktsample.data.login.LoginRequest
import com.example.ktsample.data.login.LoginResponse
import kotlinx.coroutines.flow.Flow

interface DataRepositoryImpl {

    /***
     * 获取远程服务接口API
     */
    fun <T> getRemoteService(service: Class<T>): T
}