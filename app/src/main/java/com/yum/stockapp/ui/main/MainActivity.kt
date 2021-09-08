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
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.yum.stockapp.R
import com.yum.stockapp.data.model.StockCompanyType
import com.yum.stockapp.data.model.StockFilter
import com.yum.stockapp.di.module.PERCENTAGE_KEY
import com.yum.stockapp.di.module.PRICE_KEY
import com.yum.stockapp.ui.details.DetailsActivity
import com.yum.stockapp.ui.details.DetailsActivity.Companion.STOCK_ID
import com.yum.stockapp.ui.main.stocklist.RecyclerViewAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable.just
import io.reactivex.rxkotlin.Observables
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var searchTextView: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var companyTypesFilter: ChipGroup

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    @field:Named(PRICE_KEY)
    lateinit var priceFormatter: NumberFormat

    @Inject
    @field:Named(PERCENTAGE_KEY)
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
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val adapter = RecyclerViewAdapter(priceFormatter, percentageFormatter, Glide.with(this)) {
            mainViewModel.navigateToDetails(it)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        companyTypesFilter = findViewById(R.id.companyTypesFilter)
        var chipsCache = emptyList<Chip>()
        mainViewModel.getFilterCompanyTypes().observe(this, { compnentTypes ->
            companyTypesFilter.removeAllViews()
            compnentTypes.mapIndexed { index, type ->
                Chip(this).also { it ->
                    it.text = type.first.name
                    it.id = index
                    it.isCheckable = true
                    it.isChecked = type.second
                    it.setChipDrawable(ChipDrawable.createFromAttributes(this,
                        null,
                        0,
                        R.style.Widget_App_ChipSearch))
                    it.setOnCheckedChangeListener { _, _ ->
                        chipsCache.filter { it.isChecked }.map { it.text.toString() }
                            .map { StockCompanyType(it) }.toSet().let {
                                mainViewModel.setFilterCompanyType(it)
                            }
                    }
                }
            }.also { chipsCache = it }.forEach(companyTypesFilter::addView)
        })

        mainViewModel.getStockInfo().observe(this, {
            adapter.stockInfoList = it
            progressBar.visibility = View.GONE
            mainViewModel.refreshFilter()
        })

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainViewModel.setFilterName(p0.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

        searchTextView.addTextChangedListener(textWatcher)

        mainViewModel.getFilter().observe(this,
            {
                updateFilter(adapter, it.name, it.companyType)
            })

        mainViewModel.naviageDetailsEvent().observe(this,
            {
                it?.let {
                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra(STOCK_ID, it)
                    startActivity(intent)
                }
            })
    }

    fun updateFilter(
        adapter: RecyclerViewAdapter,
        name: String,
        companyTypes: Set<StockCompanyType>,
    ) {
        adapter.stockInfoListFiltered = if (name.isEmpty() && companyTypes.isEmpty()) {
            Optional.empty()
        } else {
            Optional.of(adapter.stockInfoList
                .filter {
                    var hasNameMatch = name.isEmpty().or(it.name.startsWith(name, true))
                    var hasTypeMatch = companyTypes.isEmpty()
                        .or(it.companyType.intersect(companyTypes).isNotEmpty())

                    hasNameMatch.and(hasTypeMatch)
                })
        }

        adapter.notifyDataSetChanged()
    }
}