package com.yum.stockapp.data.dao

interface Cache<T> {
    operator fun get(key: String): T?

    fun getOrDefault(key: String, defaultValue: T): T

    fun putAndGet(key: String, newValue: T): T
}
