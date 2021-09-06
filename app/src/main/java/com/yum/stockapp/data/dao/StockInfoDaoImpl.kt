package com.yum.stockapp.data.dao

import android.util.Log
import com.yum.stockapp.data.api.StockTickerAPI
import com.yum.stockapp.data.api.model.StockTickerEntry
import com.yum.stockapp.data.model.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.net.URL
import java.util.*

class StockInfoDaoImpl constructor(val api: StockTickerAPI): StockInfoDao {
    private fun getStockDiff(id: String): StockPriceDiff {
        return StockPriceDiff(BigDecimal("1.0"))
    }

    private fun getDetails(id: String): Optional<StockDetails> {
        return Optional.of(StockDetails(StockPrice(BigDecimal("100")), "Somewhere streeet",
            URL("https://interviews.yum.dev/static/images/yum_logo.png"),
            URL("https://www.yum.com")))
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
        .flatMapIterable { it }
        .map {
            Observable.zip(
                Observable.just(it),
                Observable.fromCallable{
                    getStockDiff(it.id)
                },
                Observable.fromCallable{
                    getDetails(it.id)
                },
                {entry, priceChange, details -> createStockInfo(entry, priceChange, details)},
            )
        }.flatMap {
            it.toFlowable(BackpressureStrategy.MISSING)
        }
        .toList()
        .toFlowable()
        .observeOn(AndroidSchedulers.mainThread())

    private var logger = api.observeWebSocketEvent().observeOn(AndroidSchedulers.mainThread()).subscribe({
        Log.e("TAG", it.toString())
    })

    override fun getStockInfo() : Flowable<List<StockInfo>> {
        return flowable
    }


}


