package com.yum.stockapp.data.dao

import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.api.model.StockTickerEntry
import com.yum.stockapp.data.model.*
import com.yum.stockapp.utils.Cache
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import java.util.*

class StockInfoDaoImpl(
    val cache: Cache<StockDetails>,
    val api: StockTickerAPI,
    private val detailsApi: StockDetailsAPI,
    private val stockPriceDiff: StockPriceDiffTracker,
) : StockInfoDao {
    private fun getStockDiff(id: String, currentPrice: StockPrice): Observable<StockPriceDiff> {
        return stockPriceDiff.getDiff(id, currentPrice)
    }

    private fun getStockDetails(id: String): Observable<Optional<StockDetails>> {
        val detailsNetwork = detailsApi.getStockDetail(id).map {
            val details =
                StockDetails(StockPrice(it.allTimeHigh.value), it.address, it.imageUrl, it.website)
            cache.putAndGet(id, details)
            Optional.of(details)
        }.subscribeOn(Schedulers.io()).toObservable()
        val detailsCache = Observable.fromCallable {
            cache[id]?.let { Observable.just(Optional.of(it)) } ?: Observable.empty()
        }.subscribeOn(Schedulers.io()).flatMap { it }

        //TODO : cache invalidation
        return Observable.concat(detailsCache, detailsNetwork).firstOrError().map { it }
            .toObservable()
    }

    private var centrallistener = BehaviorProcessor.create<List<StockInfo>>()

    private var serverlistener = api.observeStockChange()
        .subscribeOn(Schedulers.io())
        .flatMap {
            Observable
                .just(it)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .concatMapIterable { it }
                .map {
                    Observable.zip(
                        Observable.just(it),
                        getStockDiff(it.id, StockPrice(it.price.value)),
                        getStockDetails(it.id),
                        this::createStockInfo
                    ).subscribeOn(Schedulers.io())
                }.concatMap { it }.reduce(mutableListOf<StockInfo>(), { acc, item ->
                    acc.apply { acc.add(item) }
                }).toFlowable()
        }
        .subscribe(centrallistener)

    override fun getStockInfo(): Flowable<List<StockInfo>> = centrallistener.subscribeOn(Schedulers.io())

    private fun createStockInfo(
        entry: StockTickerEntry,
        priceChange: StockPriceDiff,
        details: Optional<StockDetails>,
    ): StockInfo {
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


