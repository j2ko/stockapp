package com.yum.stockapp.di.module

import com.yum.stockapp.di.scope.ActivityScope
import com.yum.stockapp.ui.details.DetailsActivity
import com.yum.stockapp.ui.main.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity() : DetailsActivity
}