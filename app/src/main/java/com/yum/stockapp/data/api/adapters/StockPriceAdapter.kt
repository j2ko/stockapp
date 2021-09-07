package com.yum.stockapp.data.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.yum.stockapp.data.api.model.StockPrice
import java.math.BigDecimal

class StockPriceAdapter {
    @ToJson
    fun toJson(value: StockPrice): String {
        return value.toString()
    }

    @FromJson
    fun fromJson(value: String): StockPrice {
        return StockPrice(BigDecimal(value))
    }
}