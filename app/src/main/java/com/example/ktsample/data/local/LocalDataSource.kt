package com.example.ktsample.data.local

import com.example.ktsample.data.city.City
import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.login.OAuthCodeRequest
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.data.login.OAuthTokenResponse
import com.example.ktsample.data.pokemon.Pokemon
import com.example.ktsample.data.repository.DataPackageState
import com.example.ktsample.data.repository.IDataSource
import com.example.ktsample.data.repository.ResultPackage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private var sqLiteProvider: SQLiteProvider
): IDataSource {

    private val TAG = "LocalDataSource"

    override suspend fun getCities(): ResultPackage<CityList> {
        val cityList = ArrayList<City>(emptyList())
        cityList.add(City("1", "LocalCity"))
        return ResultPackage(
            state = DataPackageState.SUCCEED,
            source = getCode(),
            data = CityList(cityList)
        )
    }

    override suspend fun getOAuthToken(oAuthTokenRequest: OAuthTokenRequest): ResultPackage<OAuthTokenResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonList(page: Int): List<Pokemon> {
        TODO("Not yet implemented")
    }

    override fun getCode(): String {
        return TAG
    }
}