package com.yum.stockapp.di.module

import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.dao.StockInfoDao
import com.yum.stockapp.data.dao.StockInfoDaoImpl
import com.yum.stockapp.data.repository.*
import com.yum.stockapp.di.scope.ActivityScope
import com.yum.stockapp.ui.main.MainActivity
import com.yum.stockapp.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {
    @Provides
    fun provideMainViewModel(repo: StockInfoRepository, filterRepo: FilterRepository): MainViewModel {
        return MainViewModel(repo, filterRepo)
    }

    @Provides
    fun provideFilterStorage(sharedpreferences: SharedPreferences): FilterStorage {
        return FilterStorageSharedPreferenceBased(sharedpreferences)
    }

    @Provides
    fun provideFilterRepository(storage: FilterStorage): FilterRepository {
        return FilterRepositoryImpl(storage)
    }
}
