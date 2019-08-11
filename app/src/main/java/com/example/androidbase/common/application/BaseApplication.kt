package com.example.androidbase.common.application

import android.content.Context
import androidx.multidex.MultiDex
import com.example.androidbase.common.constants.API_URL
import com.example.androidbase.common.di.component.AppComponent
import com.example.androidbase.common.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .apiUrl(API_URL)
            .build()
        return appComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}