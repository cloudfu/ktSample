package com.example.ktsample.data.local

import com.example.ktsample.data.city.City
import com.example.ktsample.data.city.CityList
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

    override fun getCode(): String {
        return TAG
    }
}