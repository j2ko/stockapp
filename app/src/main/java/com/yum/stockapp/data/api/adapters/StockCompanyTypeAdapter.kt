package com.yum.stockapp.data.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.yum.stockapp.data.api.model.StockCompanyType

class StockCompanyTypeAdapter {
    @ToJson
    fun toJson(value: StockCompanyType): String {
        return value.name
    }

    @FromJson
    fun fromJson(value: String): StockCompanyType {
        return StockCompanyType(value)
    }
}
