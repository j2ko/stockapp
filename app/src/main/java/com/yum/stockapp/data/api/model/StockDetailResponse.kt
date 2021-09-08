package com.yum.stockapp.data.api.model

import java.net.URI

data class StockDetailResponse(
    val id: String,
    val name: String,
    val price: StockPrice,
    val companyType: Set<StockCompanyType>,
    val allTimeHigh: StockPrice,
    val address: String,
    val imageUrl: URI,
    val website: URI,
)