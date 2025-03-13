package com.example.ktsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class KtApplication: Application() {
    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
            // 在调试模式下，使用 DebugTree 来打印日志
            Timber.plant(Timber.DebugTree())
//        } else {
//            // 在发布模式下，可以自定义一个 Tree 来处理日志，例如将日志发送到服务器
//            Timber.plant(CrashReportingTree())
//        }
    }
}