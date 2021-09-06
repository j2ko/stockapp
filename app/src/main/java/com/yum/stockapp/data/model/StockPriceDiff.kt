package com.yum.stockapp.data.model

import java.math.BigDecimal
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
}