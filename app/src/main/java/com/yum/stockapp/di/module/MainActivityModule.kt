package com.yum.stockapp.di.module

import android.content.SharedPreferences
import com.yum.stockapp.data.repository.*
import com.yum.stockapp.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideMainViewModel(
        repo: StockInfoRepository,
        filterRepo: FilterRepository,
    ): MainViewModel {
        return MainViewModel(repo)
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
