package com.yum.stockapp.di.module

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yum.stockapp.di.ViewModelKey
import com.yum.stockapp.ui.StockViewModelFactory
import com.yum.stockapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: StockViewModelFactory): ViewModelProvider.Factory
}