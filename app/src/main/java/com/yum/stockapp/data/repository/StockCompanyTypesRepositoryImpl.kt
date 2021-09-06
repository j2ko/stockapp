package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockCompanyType
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single

class StockCompanyTypesRepositoryImpl: StockCompanyTypesRepository {
    override fun getCompanyTypes(): @NonNull Single<Set<StockCompanyType>> {
        TODO("not implemented")
    }
}