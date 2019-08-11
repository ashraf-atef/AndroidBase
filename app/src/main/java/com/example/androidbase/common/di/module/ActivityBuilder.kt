package com.example.androidbase.common.di.module

import com.example.androidbase.pagination.DataActivity
import com.example.androidbase.pagination.DataActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [DataActivityModule::class])
    abstract fun bindDataActivity(): DataActivity
}
