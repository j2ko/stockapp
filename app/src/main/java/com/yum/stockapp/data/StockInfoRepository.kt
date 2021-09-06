package com.yum.stockapp.data

import com.yum.stockapp.data.model.StockInfo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface StockInfoRepository {
    fun getStockInfoList(): Flowable<List<StockInfo>>

    fun getStockInfo(id : String): Flowable<StockInfo>
}