package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockCompanyType
import io.reactivex.Single

interface StockCompanyTypesRepository {
    fun getCompanyTypes(): Single<Set<StockCompanyType>>
}