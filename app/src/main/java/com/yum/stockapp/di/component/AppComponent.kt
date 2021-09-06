package com.yum.stockapp.di.component

import android.app.Application
import com.yum.stockapp.StockApplication
import com.yum.stockapp.di.module.APIModule
import com.yum.stockapp.di.module.ActivityBuildersModule
import com.yum.stockapp.di.module.AppModule
import com.yum.stockapp.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        APIModule::class,
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        MainActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<StockApplication> {
    override fun inject(instance: StockApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}