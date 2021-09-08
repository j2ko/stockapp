package com.yum.stockapp

import com.yum.stockapp.data.model.StockPriceDiff
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import com.yum.stockapp.data.model.StockPrice as StockPrice1

class PriceDiffTest {
    @Test
    fun `should get 20% change if 120 value provided after 100`() {
        val differ1 = StockPriceDiff.percents(
            StockPrice1(BigDecimal.valueOf(125)),
            StockPrice1(BigDecimal.valueOf(100)))
        Assert.assertEquals(differ1.value, BigDecimal("0.200000"))

        val differ2 = StockPriceDiff.percents(
            StockPrice1(BigDecimal.valueOf(100)),
            StockPrice1(BigDecimal.valueOf(125)))
        Assert.assertEquals(differ2.value, BigDecimal.valueOf(-0.25))
    }
}