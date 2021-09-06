package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockInfo
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class StockInfoRepositoryImpl @Inject constructor(): StockInfoRepository {
    override fun getStockInfoList(): Flowable<List<StockInfo>> {
        TODO("Not yet implemented")
    }

    override fun getStockInfo(id: String): Flowable<StockInfo> {
        TODO("Not yet implemented")
    }
}