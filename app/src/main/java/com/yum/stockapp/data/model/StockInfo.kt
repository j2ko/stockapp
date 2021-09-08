package com.yum.stockapp.data.model

import java.util.Optional


data class StockInfo(
    val id: String,
    val name: String,
    val price: StockPrice,
    val priceChange: StockPriceDiff,
    val companyType: Set<StockCompanyType>,
    val details: Optional<StockDetails>,
)