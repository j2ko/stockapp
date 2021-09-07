package com.yum.stockapp.di.module

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.*
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.yum.stockapp.BuildConfig
import com.yum.stockapp.data.api.adapters.StockCompanyTypeAdapter
import com.yum.stockapp.data.api.adapters.StockPriceAdapter
import com.yum.stockapp.data.api.adapters.UriAdapter
import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.StockTickerAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class APIModule {
    @Provides
    @Singleton
    fun providesStockDetailsAPI(retrofit: Retrofit): StockDetailsAPI = retrofit.create(StockDetailsAPI::class.java)


    @Provides
    @Singleton
    fun providesStockTickerWebSocket(scarlet: Scarlet) = scarlet.create<StockTickerAPI>()

    @Provides
    @Singleton
    fun provideWebSocketLifecycle(application : Application): Lifecycle = AndroidLifecycle.ofApplicationForeground(application)

    @Provides
    @Singleton
    fun providesMoshi() : Moshi {
        return Moshi.Builder()
            .add(UriAdapter())
            .add(StockPriceAdapter())
            .add(StockCompanyTypeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInterface(client : OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_REST_API_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(moshi: Moshi) : Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun providesScarletInterface(livecycle: Lifecycle, websocketFactory: WebSocket.Factory, messageAdapter: MessageAdapter.Factory, streamAdapter: StreamAdapter.Factory): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(websocketFactory)
            .addMessageAdapterFactory(messageAdapter)
            .addStreamAdapterFactory(streamAdapter)
            .lifecycle(livecycle)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient() = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideWebSocketFactory(okHttpClient: OkHttpClient) = okHttpClient.newWebSocketFactory(BuildConfig.STOCKS_WS_URL)

    @Provides
    @Singleton
    fun provideStreamAdapterFactory(): StreamAdapter.Factory {
        return RxJava2StreamAdapterFactory()
    }

    @Provides
    @Singleton
    fun providesMessageAdapterFactory(moshi: Moshi) : MessageAdapter.Factory {
        return MoshiMessageAdapter.Factory(moshi)
    }
}