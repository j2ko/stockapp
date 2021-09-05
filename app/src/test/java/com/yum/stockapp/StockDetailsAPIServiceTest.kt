package com.yum.stockapp

import com.squareup.moshi.Moshi
import com.yum.stockapp.data.CompanyTypeAdapter
import com.yum.stockapp.data.StockPriceAdapter
import com.yum.stockapp.data.URLAdapter
import com.yum.stockapp.data.api.StockDetailsAPI
import com.yum.stockapp.data.model.CompanyType
import com.yum.stockapp.data.model.StockDetailResponse
import com.yum.stockapp.data.model.StockPrice
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal
import java.net.URL
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
        .add(CompanyTypeAdapter())
        .add(StockPriceAdapter())
        .add(URLAdapter())
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
        val testObject = StockDetailResponse(
            "YUM",
            "Yum! Brands, Inc.",
            StockPrice(BigDecimal("86.94764755999583")),
            listOf(CompanyType("Food"), CompanyType("Tech")),
            StockPrice(BigDecimal("105.44")),
            "1441 Gardiner Lane Louisville, KY 40213 United States",
            URL("https://interviews.yum.dev/static/images/yum_logo.png"),
            URL("https://www.yum.com")
        );

        mockWebServer.enqueueResponseFromFile("stockdetail_valid_response.json", 200)
        val callback = TestObserver<StockDetailResponse>()

        val returnObject = api.getStockDetail("ANY").subscribe(callback)

        callback.await()
        callback.assertValue(testObject)
    }
}

internal fun MockWebServer.enqueueResponseFromFile(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("$fileName")

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}