package com.yum.stockapp.data.model

import android.util.Log
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.NumberFormat

data class StockPriceDiff(
    val value: BigDecimal,
) {
    fun format(percentageFormatter: NumberFormat): String {
        return percentageFormatter.format(value)
    }

    fun isNegative(): Boolean {
        return value < BigDecimal.ZERO
    }

    companion object {
        fun percents(newPrice: StockPrice, oldPrice: StockPrice): StockPriceDiff {
            return StockPriceDiff(
                newPrice.value
                    .subtract(oldPrice.value)
                    .divide(newPrice.value, 6, RoundingMode.HALF_UP)
            )
        }
    }
}