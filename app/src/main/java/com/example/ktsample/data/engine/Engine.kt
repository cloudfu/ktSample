package com.example.ktsample.data.engine

import android.util.Log
import com.example.ktsample.di.BindElectricEngine
import com.example.ktsample.di.BindGasEngine
import javax.inject.Inject

class Truck @Inject constructor(private var driver: Driver) {

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine

    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    fun deliver(){
        Log.i("Truck", "Driven by ${driver.name}")
        electricEngine.start()
        electricEngine.shutdown()
        gasEngine.start()
        gasEngine.shutdown()
    }
}

class Driver @Inject constructor(){
    var name = "Hua"
}

interface Engine{
    fun start()
    fun shutdown()
}


class GasEngine @Inject constructor(): Engine {
    override fun start() {
        Log.i("GasEngine", "start")
    }

    override fun shutdown() {
        Log.i("GasEngine", "shutdown")
    }
}

class ElectricEngine @Inject constructor(): Engine {
    override fun start() {
        Log.i("ElectricEngine", "start")
    }

    override fun shutdown() {
        Log.i("ElectricEngine", "shutdown")
    }
}