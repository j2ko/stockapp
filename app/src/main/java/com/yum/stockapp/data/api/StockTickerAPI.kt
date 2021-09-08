package com.yum.stockapp.data.api

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.yum.stockapp.data.api.model.StockTickerEntry
import io.reactivex.Flowable

interface StockTickerAPI {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

    @Receive
    fun observeStockChange(): Flowable<List<StockTickerEntry>>
}
