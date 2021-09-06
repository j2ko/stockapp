package com.yum.stockapp.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.MessageAdapter
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.yum.stockapp.BuildConfig
import com.yum.stockapp.data.api.adapters.StockCompanyTypeAdapter
import com.yum.stockapp.data.api.adapters.StockPriceAdapter
import com.yum.stockapp.data.api.adapters.URLAdapter
import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.StockTickerAPI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class APIModule {
    @Provides
    @Reusable
    fun providesStockDetailsAPI(retrofit: Retrofit): StockDetailsAPI = retrofit.create(StockDetailsAPI::class.java)


    @Provides
    @Reusable
    fun providesStockTickerWebSocket(scarlet: Scarlet) = scarlet.create<StockTickerAPI>()

    @Provides
    @Reusable
    fun providesMoshi() : Moshi {
        return Moshi.Builder()
            .add(URLAdapter())
            .add(StockPriceAdapter())
            .add(StockCompanyTypeAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Reusable
    fun providesRetrofitInterface(client : OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_REST_API_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Reusable
    fun providesConverterFactory(moshi: Moshi) : Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Reusable
    fun providesScarletInterface(websocketFactory: WebSocket.Factory, messageAdapter: MessageAdapter.Factory): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(websocketFactory)
            .addMessageAdapterFactory(messageAdapter)
            .build()
    }

    @Provides
    @Reusable
    fun providesOkHttpClient() = OkHttpClient.Builder().build()

    @Provides
    @Reusable
    fun provideWebSocketFactory(okHttpClient: OkHttpClient) = okHttpClient.newWebSocketFactory(BuildConfig.STOCKS_WS_URL)

    @Provides
    @Reusable
    fun providesMessageAdapterFactory(moshi: Moshi) = MoshiMessageAdapter.Factory(moshi)
}