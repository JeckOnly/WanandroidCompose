package com.jeckonly.wanandroidcompose.di

import android.app.Application
import com.jeckonly.wanandroidcompose.data.remote.WACApi
import com.jeckonly.wanandroidcompose.data.remote.intercepter.AddCookiesInterceptor
import com.jeckonly.wanandroidcompose.data.remote.intercepter.SaveCookiesInterceptor
import com.jeckonly.wanandroidcompose.data.remote_news.NewsApi
import com.jeckonly.wanandroidcompose.data.remote_news.intercepter.AddHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("WACApi")
    fun provideStockApi(app: Application): WACApi {
        return Retrofit.Builder()
            .baseUrl(WACApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
                .addInterceptor(SaveCookiesInterceptor(app))
                .addInterceptor(AddCookiesInterceptor(app))
                .build())
            .build()
            .create()
    }

    @Provides
    @Singleton
    @Named("NewsApi")
    fun provideNewsApi(app: Application): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(AddHeaderInterceptor())
                .build())
            .build()
            .create()
    }

//    @Provides
//    @Singleton
//    fun provideStockDatabase(app: Application): StockDatabase {
//        return Room.databaseBuilder(
//            app,
//            StockDatabase::class.java,
//            "stockdb.db"
//        ).build()
//    }
}