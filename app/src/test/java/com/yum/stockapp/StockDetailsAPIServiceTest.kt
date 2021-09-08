package com.yum.stockapp

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yum.stockapp.data.api.adapters.StockCompanyTypeAdapter
import com.yum.stockapp.data.api.adapters.StockPriceAdapter
import com.yum.stockapp.data.api.adapters.UriAdapter
import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.api.model.StockCompanyType
import com.yum.stockapp.data.api.model.StockDetailResponse
import com.yum.stockapp.data.api.model.StockPrice
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class StockDetailsAPIServiceTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(StockPriceAdapter())
        .add(StockCompanyTypeAdapter())
        .add(UriAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(StockDetailsAPI::class.java)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch stock detail correctly with correct data`() {
        mockWebServer.enqueueResponseFromFile("stockdetail_valid_response.json", 200)
        api.getStockDetail("ANY").test().assertNoErrors()
    }

    @Test
    fun `should fetch stock detail correctly with correct data and return response object`() {
        val testObject = StockDetailResponse("YUM",
            "Yum! Brands, Inc.",
            StockPrice(BigDecimal("86.94764755999583")),
            setOf(StockCompanyType("Food"), StockCompanyType("Tech")),
            StockPrice(BigDecimal("105.44")),
            "1441 Gardiner Lane Louisville, KY 40213 United States",
            Uri.parse("https://interviews.yum.dev/static/images/yum_logo.png"),
            Uri.parse("https://www.yum.com"))

        mockWebServer.enqueueResponseFromFile("stockdetail_valid_response.json", 200)
        val callback = TestObserver<StockDetailResponse>()

        val returnObject = api.getStockDetail("ANY").subscribe(callback)

        callback.await()
        callback.assertValue(testObject)
    }
}

internal fun MockWebServer.enqueueResponseFromFile(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}