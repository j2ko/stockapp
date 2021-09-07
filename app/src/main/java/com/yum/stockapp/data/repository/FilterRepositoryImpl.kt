package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockFilter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class FilterRepositoryImpl(private val storage: FilterStorage): FilterRepository {
    override fun getFilter(): Observable<StockFilter> {
        return Observable.fromCallable {
            storage.read().orElse(StockFilter("", emptySet()))
        }
    }

    override fun setFilter(filter: StockFilter): Single<Boolean> {
        return Single.fromCallable { storage.write(filter) }
    }
}