package com.yum.stockapp.data.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.yum.stockapp.data.api.model.StockPrice
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

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
