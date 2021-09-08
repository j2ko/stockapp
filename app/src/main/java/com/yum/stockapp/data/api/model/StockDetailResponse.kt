package com.yum.stockapp.data.api.model

import android.net.Uri

data class StockDetailResponse(
    val id: String,
    val name: String,
    val price: StockPrice,
    val companyType: Set<StockCompanyType>,
    val allTimeHigh: StockPrice,
    val address: String,
    val imageUrl: Uri,
    val website: Uri,
)