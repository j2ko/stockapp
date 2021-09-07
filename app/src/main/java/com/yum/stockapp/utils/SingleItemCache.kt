package com.yum.stockapp.utils
//TODO : optimize code
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

    override fun remove(key: String): T? {
        return internalCache.remove(key)
    }

    override fun set(key: String, value: T) {
        internalCache[key] = value
    }
}