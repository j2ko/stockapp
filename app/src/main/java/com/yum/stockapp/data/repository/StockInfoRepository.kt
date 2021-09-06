package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockInfo
import io.reactivex.Flowable

interface StockInfoRepository {
    fun getStockInfoList(): Flowable<List<StockInfo>>

    fun getStockInfo(id : String): Flowable<StockInfo>
}