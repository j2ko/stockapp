package com.yum.stockapp.data.model

data class StockFilter(
    val name: String,
    val companyType: Set<StockCompanyType>,
)