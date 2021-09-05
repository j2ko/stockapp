package com.yum.stockapp.data.model

import java.net.URL

data class StockDetailResponse (
    val id: String,
    val name: String,
    val price: StockPrice,
    val companyType: List<CompanyType>,
    val allTimeHigh: StockPrice,
    val address: String,
    val imageUrl: URL,
    val website: URL,
)