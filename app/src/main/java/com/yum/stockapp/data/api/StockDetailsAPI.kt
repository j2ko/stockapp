package com.yum.stockapp.data.api

import com.yum.stockapp.data.api.model.StockDetailResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface StockDetailsAPI {
    @GET("stocks/{name}")
    fun getStockDetail(@Path("name") companyName: String): Single<StockDetailResponse>
}