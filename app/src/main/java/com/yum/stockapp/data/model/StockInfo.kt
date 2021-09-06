package com.yum.stockapp.data.model

import io.reactivex.rxjava3.core.Maybe
import java.util.*


data class StockInfo (
    val id : String,
    val name: String,
    val price: StockPrice,
    val priceChange: StockPriceDiff,
    val companyType: Set<StockCompanyType>,
    val details: Optional<StockDetails>
)