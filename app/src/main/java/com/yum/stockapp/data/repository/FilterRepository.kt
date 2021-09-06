package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockFilter
import io.reactivex.Observable
import io.reactivex.Single


interface FilterRepository {
    fun getFilter() : Observable<StockFilter>
    fun setFilter(filter: StockFilter): Single<Boolean>
}