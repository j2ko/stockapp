package com.yum.stockapp.data.model

import android.net.Uri

data class StockDetails (
    val allTimeHigh : StockPrice,
    val address: String,
    val imageUrl: Uri,
    val website: Uri
)