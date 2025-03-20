package com.example.ktsample.data.remote

import android.util.Log
import com.example.ktsample.data.api.PokemonService
import com.example.ktsample.data.api.OAuthApiService
import com.example.ktsample.data.repository.ResultPackage
import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.data.login.OAuthTokenResponse
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.data.pokemon.PokemonResponse
import com.example.ktsample.data.repository.DataPackageState
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val networkProvider: NetworkProvider
) {

    private val TAG = "RemoteDataSource"

    suspend fun getCities(): ResultPackage<CityList> {
        val pokemonService = networkProvider.createApiService(PokemonService::class.java)

        try {
            val response = pokemonService.getCities()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            }
        } catch (_: IOException) {
        }


        return ResultPackage(
                    data = null,
                    state = DataPackageState.SERVER_FAILED
                )
//        return when(val result = networkProvider.request(apiService::getCities)){
//            is List<*> ->{
//                ResultPackage(
//                    state = DataPackageState.SUCCEED,
//                    source = getCode(),
//                    data = CityList(result as List<City>)
//                )
//            }
//            // ErrCode
//            is Int -> {
//                ResultPackage(
//                    data = null,
//                    state = DataPackageState.SERVER_FAILED
//                )
//            }
//            else -> {
//                ResultPackage(
//                    data = null,
//                    state = DataPackageState.UNKNOWN_ERROR
//                )
//            }
//        }
    }

    suspend fun getOAuthToken(oAuthTokenRequest: OAuthTokenRequest): ApiResponse<OAuthTokenResponse> {
        val oAuthApiService = networkProvider.createApiService(OAuthApiService::class.java)
        val response: ApiResponse<OAuthTokenResponse> = oAuthApiService.getOAuthUserToken(
            oAuthTokenRequest.clientId,
            oAuthTokenRequest.clientSecret,
            oAuthTokenRequest.code,
            oAuthTokenRequest.redirectUri
        )
        Timber.d(response.toString())
        return response

//        response.enqueue(object : retrofit2.Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.isSuccessful) {
//                    val formResponse = response.body()
//                    if (formResponse != null) {
//                    }
//                } else {
//                    println("Request failed: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                println("Network error: ${t.message}")
//            }
//        })
//        Log.i("TAG", response.body().toString());
//        val responseCode = response.code()
//        if (response.isSuccessful) {
//            val customTabsIntent = CustomTabsIntent.Builder().build()
//            response.body()
//        }




    }

    suspend fun fetchPokemonList(page: Int): ApiResponse<PokemonResponse> {
        val pokemonService = networkProvider.createApiService(PokemonService::class.java)

        try {
            val pageSize  = 40
            val rows = (page + 1 ) * pageSize
            val result = pokemonService.fetchPokemonList(PokemonService.hostUri,0,rows)
            val pokemon: PokemonResponse? = result.body()
            val count = pokemon?.count ?:0
            pokemon?.let {
                return ApiResponse.Success(data = pokemon)
            }
        } catch (_: IOException) {
        }
        return ApiResponse.Empty<PokemonResponse>()
    }

}