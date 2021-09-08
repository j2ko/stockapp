package com.yum.stockapp.data.repository

import com.yum.stockapp.data.model.StockFilter
import java.util.Optional

interface FilterStorage {
    fun write(value: StockFilter): Boolean
    fun read(): Optional<StockFilter>
    fun wipe(): Boolean
}