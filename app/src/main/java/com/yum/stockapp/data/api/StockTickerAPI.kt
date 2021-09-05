package com.yum.stockapp.data.api

import com.tinder.scarlet.ws.Receive
import com.yum.stockapp.data.model.StockTikerResponse
import io.reactivex.rxjava3.core.Flowable

interface StockTickerAPI {
    @Receive
    fun observeStockChange() : Flowable<StockTikerResponse>
}