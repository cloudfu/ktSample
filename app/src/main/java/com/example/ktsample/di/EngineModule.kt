package com.example.ktsample.di

import com.example.ktsample.data.engine.ElectricEngine
import com.example.ktsample.data.engine.Engine
import com.example.ktsample.data.engine.GasEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier


@Module
@InstallIn(ActivityComponent::class)
abstract class EngineModule {

    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(engine: ElectricEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(engine: GasEngine): Engine
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindGasEngine

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine

