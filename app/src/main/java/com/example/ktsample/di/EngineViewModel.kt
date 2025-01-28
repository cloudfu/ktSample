package com.example.ktsample.di

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import javax.inject.Inject

@ActivityRetainedScoped
class EngineViewModel @Inject constructor(var driver: Driver): ViewModel() {

    @Inject
    lateinit var  electricEngine: ElectricEngine
    @Inject
    lateinit var gasEngine: GasEngine
    @Inject
    lateinit var  retrofit: Retrofit

    fun deliver() {
        Log.i("Truck", "Driven by ${driver.name}")
        electricEngine.start()
        electricEngine.shutdown()
        gasEngine.start()
        gasEngine.shutdown()
        Log.i("Truck", retrofit.baseUrl().toString())
    }
}