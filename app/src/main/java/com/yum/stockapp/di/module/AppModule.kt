package com.yum.stockapp.di.module

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.dao.*
import com.yum.stockapp.data.model.StockDetails
import com.yum.stockapp.data.model.StockPrice
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.data.repository.StockInfoRepositoryImpl
import com.yum.stockapp.utils.Cache
import com.yum.stockapp.utils.SingleItemCache
import com.yum.stockapp.utils.TimeBasedCache
import dagger.Module
import dagger.Provides
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val PRICE_KEY = "PRICE_KEY"
const val PERCENTAGE_KEY = "PERCENTAGE_KEY"
const val SHARED_PREF_PATH = "PERCENTAGE_KEY"

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_PATH, MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesStockDiffTracker(cache: Cache<StockPrice>): StockPriceDiffTracker {
        return StockPriceDiffTrackerImpl(cache)
    }

    @Provides
    fun providesStockPriceCache(): Cache<StockPrice> {
        return SingleItemCache()
    }

    @Provides
    fun providesStockDetailsCache(): Cache<StockDetails> {
        return TimeBasedCache<StockDetails>(5, TimeUnit.MINUTES)
    }

    @Provides
    @Singleton
    fun provideStockInfoDao(
        cache: Cache<StockDetails>,
        api: StockTickerAPI,
        detailsApi: StockDetailsAPI,
        diffTracer: StockPriceDiffTracker,
    ): StockInfoDao {
        return StockInfoDaoImpl(cache, api, detailsApi, diffTracer)
    }

    @Provides
    @Singleton
    fun provideStockInfoRepository(dao: StockInfoDao): StockInfoRepository {
        return StockInfoRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    @Named(PRICE_KEY)
    fun providesFormatterPrice(): NumberFormat {
        return DecimalFormat("####0.0000¤").also {
            it.currency = Currency.getInstance("USD")
            it.minimumFractionDigits = 5
        }
    }

    @Provides
    @Singleton
    @Named(PERCENTAGE_KEY)
    fun providesFormatterPercentage(): NumberFormat {
        return DecimalFormat("+#00.0000%;-#00.0000%")
    }
}