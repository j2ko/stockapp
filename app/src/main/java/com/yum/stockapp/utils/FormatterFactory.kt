package com.yum.stockapp.utils

import android.icu.number.NumberFormatter

interface FormatterFactory {
    fun createPriceFormatter() : NumberFormatter
    fun createPercentageFormatter() : NumberFormatter
}