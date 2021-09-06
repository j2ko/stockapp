package com.yum.stockapp.data.repository

import com.yum.stockapp.data.dao.StockInfoDao
import com.yum.stockapp.data.model.StockInfo
import io.reactivex.Flowable
import javax.inject.Inject

class StockInfoRepositoryImpl @Inject constructor(val dao: StockInfoDao): StockInfoRepository {
    override fun getStockInfoList(): Flowable<List<StockInfo>> {
        return dao.getStockInfo()
    }

    override fun getStockInfo(id: String): Flowable<StockInfo> {
        TODO("Not yet implemented")
    }
}