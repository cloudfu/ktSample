//package com.example.ktsample.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ActivityRetainedComponent
//import dagger.hilt.android.scopes.ActivityRetainedScoped
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//import javax.inject.Singleton
//
///**
// * Description:
// *
// * @param
// * @return
// */
//@InstallIn(SingletonComponent::class)
//@Module
//class NetworkModule {
//
//    @Singleton
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
//            .writeTimeout(20, TimeUnit.SECONDS)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun providerRetrofit(httpClient: OkHttpClient): Retrofit{
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://pokeapi.co/api/v2/")
////            .baseUrl("https://jenly1314.gitlab.io/")
//            .client(httpClient)
//            .build()
//    }
//}
//
