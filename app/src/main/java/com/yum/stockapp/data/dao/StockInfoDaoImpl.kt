package com.yum.stockapp.data.dao

import android.util.Log
import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.api.model.StockTickerEntry
import com.yum.stockapp.data.model.*
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.concatAll
import io.reactivex.rxkotlin.merge
import io.reactivex.rxkotlin.mergeAll
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.net.URL
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

    private fun createStockInfo(entry: StockTickerEntry, priceChange : StockPriceDiff, details: Optional<StockDetails>) : StockInfo {
        return StockInfo(
            entry.id,
            entry.name,
            StockPrice(entry.price.value),
            priceChange,
            entry.companyType.map { StockCompanyType(it.name) }.toSet(),
            details
        )
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
                        { entry, priceChange, details ->
                            createStockInfo(
                                entry,
                                priceChange,
                                details
                            )
                        }
                    )
                }.flatMap { it }.reduce(mutableListOf<StockInfo>(), { acc, item ->
                    acc.apply { acc.add(item) }
                }).toFlowable()
        }

    private var logger = api.observeWebSocketEvent().observeOn(AndroidSchedulers.mainThread()).subscribe({
        Log.e("TAG", it.toString())
    })

    override fun getStockInfo() : Flowable<List<StockInfo>> {
        return flowable
    }


}


