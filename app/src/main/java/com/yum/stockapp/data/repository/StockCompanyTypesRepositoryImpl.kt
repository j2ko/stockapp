package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockCompanyType
import io.reactivex.Single
import io.reactivex.annotations.NonNull

class StockCompanyTypesRepositoryImpl: StockCompanyTypesRepository {
    override fun getCompanyTypes(): Single<Set<StockCompanyType>> {
        TODO("not implemented")
    }
}