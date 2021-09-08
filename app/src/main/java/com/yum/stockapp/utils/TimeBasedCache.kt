package com.yum.stockapp.utils

import com.yum.stockapp.data.model.StockDetails
import java.util.*
import java.util.concurrent.TimeUnit

class TimeBasedCache<T>(amount: Long, unit: TimeUnit) : Cache<StockDetails> {
    private val timeout = unit.toMillis(amount)
    private val detailsCache = SingleItemCache<StockDetails>()
    private val detailsExpirationCache = mutableMapOf<String, Long>()

    override fun get(key: String): StockDetails? {
        return detailsExpirationCache[key]?.let {
            if (Date().time > it) {
                // Invalidate cache entry
                remove(key)
                null
            } else {
                detailsCache[key]
            }
        }
    }

    override fun getOrDefault(key: String, defaultValue: StockDetails): StockDetails {
        return get(key) ?: defaultValue
    }

    override fun putAndGet(key: String, newValue: StockDetails): StockDetails {
        val oldValue = get(key)
        set(key, newValue)

        return oldValue ?: newValue
    }

    override fun set(key: String, value: StockDetails) {
        detailsCache[key] = value
        detailsExpirationCache[key] = Date().time + timeout
    }

    override fun remove(key: String): StockDetails? {
        detailsExpirationCache.remove(key)
        return detailsCache.remove(key)
    }
}
