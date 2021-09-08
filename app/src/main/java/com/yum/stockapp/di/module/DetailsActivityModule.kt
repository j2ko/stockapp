package com.yum.stockapp.di.module

import com.yum.stockapp.ui.details.DetailsActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {
    @Provides
    fun provideTaskId(activity: DetailsActivity): String {
        return activity.intent.getStringExtra(DetailsActivity.STOCK_ID).orEmpty()
    }

}