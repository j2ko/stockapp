package com.yum.stockapp.data.api.model

data class StockTickerEntry(
    val id: String,
    val name: String,
    val price : StockPrice,
    val companyType : Set<StockCompanyType>,
);