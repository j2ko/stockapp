package com.yum.stockapp.utils

interface Cache<T> {
    operator fun get(key: String): T?

    fun getOrDefault(key: String, defaultValue: T): T

    fun putAndGet(key: String, newValue: T): T

    fun remove(key: String): T?

    operator fun set(key: String, value: T)
}
