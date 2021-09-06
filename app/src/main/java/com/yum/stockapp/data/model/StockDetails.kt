package com.yum.stockapp.data.model

import java.net.URL

data class StockDetails (
    val allTimeHigh : StockPrice,
    val address: String,
    val imageUrl: URL,
    val website: URL
)