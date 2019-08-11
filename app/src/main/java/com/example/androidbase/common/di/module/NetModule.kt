package com.example.androidbase.common.di.module

import com.example.androidbase.common.constants.API_URL_KEY
import com.example.androidbase.pagination.data.remote.DataApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
abstract class NetModule {

    @Binds
    @Named(API_URL_KEY)
    abstract fun provideApiUrl(apiUrl: String): String

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun providesOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .build()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun providesGson(): Gson {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            return gsonBuilder.create()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun providesRetrofit(
            @Named(API_URL_KEY) apiUrl: String, gson: Gson
        ): Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(apiUrl)
                .build()
        }

        @Provides
        @Reusable
        @JvmStatic
        fun providesProductsApi(retrofit: Retrofit): DataApi {
            return retrofit.create(DataApi::class.java)
        }
    }
}