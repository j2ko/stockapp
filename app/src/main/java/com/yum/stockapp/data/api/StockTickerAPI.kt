package com.yum.stockapp.data.api

import com.tinder.scarlet.ws.Receive
import com.yum.stockapp.data.api.model.StockTickerResponse
import io.reactivex.rxjava3.core.Flowable

interface StockTickerAPI {
    @Receive
    fun observeStockChange() : Flowable<StockTickerResponse>
}