package com.yum.stockapp.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.yum.stockapp.data.model.CompanyType

class CompanyTypeAdapter {
    @ToJson
    fun toJson(value: CompanyType): String {
        return value.name
    }

    @FromJson
    fun fromJson(value: String): CompanyType {
        return CompanyType(value)
    }
}