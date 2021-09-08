package com.yum.stockapp.data.dao

import android.util.Log
import com.yum.stockapp.data.model.StockPrice
import com.yum.stockapp.data.model.StockPriceDiff
import com.yum.stockapp.utils.Cache
import io.reactivex.Observable
import java.math.BigDecimal

class StockPriceDiffTrackerImpl constructor(private val cache: Cache<StockPrice>) :
    StockPriceDiffTracker {
    override fun getDiff(id: String, currentValue: StockPrice): Observable<StockPriceDiff> {
        val oldPrice = cache.putAndGet(id, currentValue)
        val stockPriceDiff = StockPriceDiff.percents(currentValue, oldPrice)
        return Observable.just(stockPriceDiff)
    }
}