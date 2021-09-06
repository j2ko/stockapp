package com.yum.stockapp.data

import com.yum.stockapp.data.model.StockCompanyType
import io.reactivex.rxjava3.core.Single

interface StockCompanyTypesRepository {
    fun getCompanyTypes(): Single<Set<StockCompanyType>>
}