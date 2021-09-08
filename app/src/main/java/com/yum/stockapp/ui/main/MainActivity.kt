package com.yum.stockapp.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.yum.stockapp.R
import com.yum.stockapp.ui.details.DetailsActivity
import com.yum.stockapp.ui.details.DetailsActivity.Companion.STOCK_ID
import com.yum.stockapp.ui.main.stocklist.RecyclerViewAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    @field:Named("PRICE")
    lateinit var priceFormatter: NumberFormat

    @Inject
    @field:Named("PERCENTAGE")
    lateinit var percentageFormatter: NumberFormat

    private val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        recyclerView = findViewById(R.id.stockList)
        // TODO: Move Glide to inject
        val adapter = RecyclerViewAdapter(priceFormatter, percentageFormatter, Glide.with(this)
        ) {
            mainViewModel.navigateToDetails(it)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel.getStockInfo().observe(this, {
            adapter.stockInfoList = it
            adapter.notifyDataSetChanged()
        })

        mainViewModel.naviageDetailsEvent().observe(this, {
            it?.let {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(STOCK_ID, it)
                startActivity(intent)
            }
        })
    }
}