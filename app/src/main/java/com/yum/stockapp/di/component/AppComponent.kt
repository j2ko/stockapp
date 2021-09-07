package com.yum.stockapp.di.component

import android.app.Application
import com.yum.stockapp.StockApplication
import com.yum.stockapp.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuildersModule::class,
        AndroidSupportInjectionModule::class,
        APIModule::class,
        AppModule::class,
        AndroidSupportInjectionModule::class,
        MainActivityModule::class,
        ViewModelModule::class
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