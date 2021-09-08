package com.yum.stockapp.di.module

import com.yum.stockapp.ui.details.DetailsActivity
import com.yum.stockapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    abstract fun contributeDetailsActivity(): DetailsActivity
}