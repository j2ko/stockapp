package com.yum.stockapp.ui.details

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yum.stockapp.R
import com.yum.stockapp.di.module.PERCENTAGE_KEY
import com.yum.stockapp.di.module.PRICE_KEY
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Named

class DetailsActivity : DaggerAppCompatActivity() {
    companion object {
        const val STOCK_ID = "STOCK_ID"
    }

    @Inject
    lateinit var stockId: String

    @Inject
    @field:Named(PRICE_KEY)
    lateinit var priceFormatter: NumberFormat

    @Inject
    @field:Named(PERCENTAGE_KEY)
    lateinit var percentageFormatter: NumberFormat

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val detailsViewModel: DetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_details)

        val glide = Glide.with(this)
        val stockCompanyLogo = findViewById<ImageView>(R.id.detailsLogo)
        val stockCompanyId = findViewById<TextView>(R.id.detailsId)
        val stockCompanyPrice = findViewById<TextView>(R.id.detailsPrice)
        val stockCompanyType = findViewById<ChipGroup>(R.id.companyTypes)
        val stockCompanyChangeIndicator = findViewById<ImageView>(R.id.detailsIndicator)
        val stockCompanyName = findViewById<TextView>(R.id.detailsName)
        val stockCompanyChangeValue = findViewById<TextView>(R.id.detailsPriceChange)
        val stockCompanyAllTimeHigh = findViewById<TextView>(R.id.detailsAllTimeHigh)
        val stockCompanyAddress = findViewById<TextView>(R.id.detailsAddress)
        val stockCompanyWebsite = findViewById<TextView>(R.id.detailsWebsite)

        stockId.let {
            detailsViewModel.getStockInfo(it).observe(this, { item ->
                stockCompanyId.text = item.id
                stockCompanyName.text = item.name
                stockCompanyChangeValue.text = item.priceChange.format(percentageFormatter)
                stockCompanyPrice.text = item.price.format(priceFormatter)
                stockCompanyType.removeAllViews().also {
                    item.companyType.map { type ->
                        Chip(this).also {
                            it.text = type.name
                        }
                    }.forEach(stockCompanyType::addView)
                }
                stockCompanyChangeIndicator?.setBackgroundResource(if (item.priceChange.isNegative())
                    R.drawable.stock_change_arrow_down else R.drawable.stock_change_arrow_up
                )
                item.details.ifPresent { details ->
                    glide.load(Uri.parse(details.imageUrl.toASCIIString())).into(stockCompanyLogo)
                    stockCompanyAddress.text = details.address
                    stockCompanyWebsite.text = details.website.toString()
                    stockCompanyAllTimeHigh.text = details.allTimeHigh.format(priceFormatter)
                }
            })
        }
    }
}