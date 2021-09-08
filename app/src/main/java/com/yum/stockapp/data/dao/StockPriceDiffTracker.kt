package com.yum.stockapp.data.dao

import com.yum.stockapp.data.model.StockPrice
import com.yum.stockapp.data.model.StockPriceDiff

import io.reactivex.Observable

interface StockPriceDiffTracker {
    fun getDiff(id: String, currentValue: StockPrice): Observable<StockPriceDiff>
}
