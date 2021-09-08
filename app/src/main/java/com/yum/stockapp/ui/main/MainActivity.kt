package com.yum.stockapp.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.yum.stockapp.R
import com.yum.stockapp.data.model.StockFilter
import com.yum.stockapp.ui.details.DetailsActivity
import com.yum.stockapp.ui.details.DetailsActivity.Companion.STOCK_ID
import com.yum.stockapp.ui.main.stocklist.RecyclerViewAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var searchTextView: EditText
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
        searchTextView = findViewById(R.id.searchField)

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

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.setFilter(p0.toString(), emptySet())

                if (p0.toString().trim().isNotEmpty()) {
                    adapter.stockInfoListFiltered = Optional.of(
                        adapter.stockInfoList
                            .filter{ it.name.startsWith(p0.toString(), true) })

                } else {
                    adapter.stockInfoListFiltered = Optional.empty()
                }
                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

        searchTextView.addTextChangedListener(textWatcher)

        mainViewModel.getFilter().observe(this, {
            if (!searchTextView.text.equals(it.name)) {
                searchTextView.setText(it.name)
            }
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