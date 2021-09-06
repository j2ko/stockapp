package com.yum.stockapp.data.dao

import com.yum.stockapp.data.model.StockInfo
import io.reactivex.Flowable

interface StockInfoDao {
    fun getStockInfo(): Flowable<List<StockInfo>>
}