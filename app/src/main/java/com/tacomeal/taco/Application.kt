package com.tacomeal.taco

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext

    }
    companion object{
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context

        val appContext : Context
            get() {
                return mContext
            }
    }
}