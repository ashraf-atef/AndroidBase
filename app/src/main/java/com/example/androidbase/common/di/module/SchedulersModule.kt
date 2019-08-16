package com.example.androidbase.common.di.module

import com.example.androidbase.common.presentationLayer.schedulers.SchedulersService
import com.example.androidbase.common.presentationLayer.schedulers.SchedulersServiceImpl
import dagger.Module
import dagger.Provides

@Module
class SchedulersModule {

    @Provides
    fun provideSchedulersService(): SchedulersService = SchedulersServiceImpl()
}