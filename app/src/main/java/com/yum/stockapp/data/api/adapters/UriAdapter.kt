package com.yum.stockapp.data.api.adapters

import android.net.Uri
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URL

class UriAdapter {
    @ToJson
    fun toJson(uri : Uri) : String = uri.toString()

    @FromJson
    fun fromJson(uri : String): Uri = Uri.parse(uri)
}