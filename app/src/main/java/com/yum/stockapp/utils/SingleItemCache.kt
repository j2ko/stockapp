package com.yum.stockapp.utils

class SingleItemCache<T> : Cache<T> {
    private val internalCache = mutableMapOf<String, T>()
    override fun get(key: String): T? {
        return internalCache[key]
    }

    override fun getOrDefault(key: String, defaultValue: T): T {
        return internalCache[key] ?: defaultValue
    }

    override fun putAndGet(key: String, newValue: T): T {
        var oldItem = internalCache[key]
        internalCache[key] = newValue
        return oldItem ?: newValue
    }

    override fun remove(key: String): T? {
        return internalCache.remove(key)
    }

    override fun set(key: String, value: T) {
        internalCache[key] = value
    }
}