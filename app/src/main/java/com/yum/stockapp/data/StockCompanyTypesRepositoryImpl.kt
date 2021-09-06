package com.yum.stockapp.data

import com.yum.stockapp.data.model.StockCompanyType
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.operators.observable.ObservableRange
import java.util.*

class StockCompanyTypesRepositoryImpl: StockCompanyTypesRepository {
    override fun getCompanyTypes(): @NonNull Single<Set<StockCompanyType>> {
        TODO("not implemented")
    }
}