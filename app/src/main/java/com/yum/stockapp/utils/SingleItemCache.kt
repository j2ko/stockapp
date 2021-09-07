package com.yum.stockapp.data.dao

import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.model.StockPrice

class SingleItemCache<T> : Cache<T> {
    private val internalCache = mutableMapOf<String, T>()
    override fun get(key: String): T? {
        return internalCache[key]
    }

    override fun getOrDefault(key: String, defaultValue: T): T {
        return internalCache[key] ?: defaultValue
    }

    override fun putAndGet(key: String, newValue: T): T {
        return internalCache[key] ?: newValue.apply { internalCache[key] = newValue }
    }
}