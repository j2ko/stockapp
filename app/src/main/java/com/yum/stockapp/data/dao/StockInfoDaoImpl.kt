package com.yum.stockapp.data.dao

import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.api.model.StockTickerEntry
import com.yum.stockapp.data.model.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class StockInfoDaoImpl constructor(val api: StockTickerAPI, val detailsApi: StockDetailsAPI, val stockPriceDiff: StockPriceDiffTracker): StockInfoDao {
    private fun getStockDiff(id: String, currentPrice: StockPrice): Observable<StockPriceDiff> {
        return stockPriceDiff.getDiff(id, currentPrice)
    }

    private fun getStockDetails(id: String): Observable<Optional<StockDetails>> {
        return detailsApi.getStockDetail(id).map {
            Optional.of(StockDetails(StockPrice(it.allTimeHigh.value), it.address, it.imageUrl, it.website))
        }.toObservable()
    }

    private var flowable : Flowable<List<StockInfo>> = api.observeStockChange()
        .subscribeOn(Schedulers.io())
        .flatMap {
            Observable
                .just(it)
                .flatMapIterable { it }
                .map {
                    Observable.zip(
                        Observable.just(it),
                        getStockDiff(it.id, StockPrice(it.price.value)),
                        getStockDetails(it.id),
                        this::createStockInfo
                    )
                }.flatMap { it }.reduce(mutableListOf<StockInfo>(), { acc, item ->
                    acc.apply { acc.add(item) }
                }).toFlowable()
        }

    override fun getStockInfo() : Flowable<List<StockInfo>> {
        return flowable
    }

   fun createStockInfo(entry: StockTickerEntry, priceChange : StockPriceDiff, details: Optional<StockDetails>) : StockInfo {
        return StockInfo(
            entry.id,
            entry.name,
            StockPrice(entry.price.value),
            priceChange,
            entry.companyType.map { StockCompanyType(it.name) }.toSet(),
            details
        )
    }
}


