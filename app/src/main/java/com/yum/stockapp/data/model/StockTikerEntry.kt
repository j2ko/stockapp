package com.yum.stockapp.data.model

data class StockTikerEntry(
    val id: String,
    val name: String,
    val price : StockPrice,
    val companyType : String,
);