package com.yum.stockapp.data.dao

import com.yum.stockapp.data.model.StockPrice
import com.yum.stockapp.data.model.StockPriceDiff
import io.reactivex.Observable

class StockPriceDiffTrackerImpl: StockPriceDiffTracker {
    val cache = mutableMapOf<String, StockPrice>()
    override fun getDiff(id: String, currentValue: StockPrice): Observable<StockPriceDiff> {
        val oldPrice = cache.getOrDefault(id, currentValue)
        cache.put(id, currentValue)
        val stockPriceDiff = StockPriceDiff.percents(currentValue, oldPrice)
        return Observable.just(stockPriceDiff)
    }
}