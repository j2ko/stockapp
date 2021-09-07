package com.yum.stockapp.data.model

import android.util.Log
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.NumberFormat

data class StockPriceDiff (
    val value : BigDecimal
) {
    fun format(percentageFormatter: NumberFormat): String {
        return percentageFormatter.format(value)
    }

    fun isNegative(): Boolean {
        return value.compareTo(BigDecimal.ZERO) < 0
    }

    companion object {
        fun percents(left: StockPrice, right: StockPrice) : StockPriceDiff {
            return StockPriceDiff(
                left.value
                    .subtract(right.value)
                    .multiply(BigDecimal.valueOf(100), MathContext(6, RoundingMode.HALF_UP))
                    .divide(left.value, 6, RoundingMode.HALF_UP)
            )
        }
    }
}