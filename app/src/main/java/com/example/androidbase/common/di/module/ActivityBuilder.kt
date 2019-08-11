package com.example.androidbase.common.di.module

import com.example.androidbase.pagination.DataActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [DataModule::class])
    abstract fun bindDataActivity(): DataActivity
}
