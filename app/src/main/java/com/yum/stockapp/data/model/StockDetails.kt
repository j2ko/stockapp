package com.yum.stockapp.data.model

import java.net.URI

data class StockDetails(
    val allTimeHigh: StockPrice,
    val address: String,
    val imageUrl: URI,
    val website: URI,
)