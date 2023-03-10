package com.flow.hugh

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setLogger()
    }

    private fun setLogger() {
        val formatStrategy = PrettyFormatStrategy
            .newBuilder()
            .showThreadInfo(false)
            .methodCount(2)
            .methodOffset(0)
            .tag("로그")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}