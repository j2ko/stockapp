package com.yum.stockapp.data.model

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

data class StockPrice (
    val value : BigDecimal
) {
    fun format(numberFormatter: NumberFormat): String? {
        return numberFormatter.format(value)
    }
}