package com.yum.stockapp.di.module

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.dao.StockInfoDao
import com.yum.stockapp.data.dao.StockInfoDaoImpl
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.data.repository.StockInfoRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) : SharedPreferences {
        // TODO: make ROOT Configurable
        return context.getSharedPreferences("ROOT", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideStockInfoDao(api: StockTickerAPI): StockInfoDao {
        return StockInfoDaoImpl(api)
    }

    @Provides
    @Singleton
    fun provideStockInfoRepository(dao : StockInfoDao): StockInfoRepository {
        return StockInfoRepositoryImpl(dao)
    }
}