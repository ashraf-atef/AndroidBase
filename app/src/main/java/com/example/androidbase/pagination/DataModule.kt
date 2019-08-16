package com.example.androidbase.pagination

import dagger.Binds
import dagger.Module

@Module
abstract class DataActivityModule {

    @Binds
    abstract fun provideDataAdapterItemClickListener(dataActivity: DataActivity): DataAdapter.ItemClickListener
}