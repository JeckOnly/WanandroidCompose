package com.jeckonly.wanandroidcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WACApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    companion object{
        private lateinit var mApplication: Application

        fun getApplication() = mApplication
    }
}