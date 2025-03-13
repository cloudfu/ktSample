package com.example.ktsample.data.api

import com.example.ktsample.data.city.City
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.data.login.OAuthTokenResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface OAuthApiService {

    companion object {
        val hostUri = "https://github.com/"
    }

    @GET("login/oauth/authorize")
    suspend fun getOAuthCode(
        @Query("client_id") client_id: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("scope") scope: String,
        @Query("state") state: String
    ): Response<String>

    // 采用 HTTP Post x-www-form-urlencoded 提交方式
    @FormUrlEncoded
    @POST("login/oauth/access_token/")
    suspend fun getOAuthUserToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String
    ): OAuthTokenResponse
}