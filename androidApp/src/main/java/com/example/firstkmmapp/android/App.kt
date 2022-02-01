package com.example.firstkmmapp.android

import android.app.Application
import com.example.firstkmmapp.di.MultiplatformSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiplatformSDK.init(this)
    }
}