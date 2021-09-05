package com.yum.stockapp.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URL

class URLAdapter {
    @ToJson
    fun toJson(url : URL) : String = url.toString()

    @FromJson
    fun fromJson(url : String): URL = URL(url)
}