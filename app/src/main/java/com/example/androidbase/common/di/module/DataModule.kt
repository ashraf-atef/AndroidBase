package com.example.androidbase.common.di.module

import android.content.Context
import com.example.androidbase.pagination.data.local.DataDao
import com.example.androidbase.common.dataLayer.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DataModule {

    @Provides
    @Reusable
    fun provideProductsDatabase(context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }

    @Provides
    fun provideProductDAO(localDatabase: LocalDatabase): DataDao = localDatabase.dataDao()
}