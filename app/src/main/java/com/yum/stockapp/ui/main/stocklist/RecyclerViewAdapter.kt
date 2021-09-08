package com.yum.stockapp.ui.main.stocklist

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yum.stockapp.R
import com.yum.stockapp.data.model.StockInfo
import java.text.NumberFormat
import java.util.*

class RecyclerViewViewHolder(
    v: View,
    priceFormatter: NumberFormat,
    percentageFormatter: NumberFormat,
    glide: RequestManager,
    listener: (String) -> Unit
) : RecyclerView.ViewHolder(v) {
    private var stockId = v.findViewById<TextView>(R.id.stockCompanyId)
    private var stockCompanyPicture = v.findViewById<ImageView>(R.id.stockCompanyLogo)
    private var stockCompanyName = v.findViewById<TextView>(R.id.stockCompanyName)
    private var stockCompanyPrice = v.findViewById<TextView>(R.id.stockCompanyPrice)
    private var stockCompanyChangeIndicator =
        v.findViewById<ImageView>(R.id.stockCompanyChangeIndicator)
    private var stockCompanyChangeValue = v.findViewById<TextView>(R.id.stockCompanyChangeValue)
    private var stockCompanyTypes = v.findViewById<ChipGroup>(R.id.companyTypes)
    private var numberFormatter = priceFormatter
    private var percentageFormatter = percentageFormatter
    private val glide = glide
    private val listener = listener

    fun bind(info: StockInfo) {
        itemView.setOnClickListener {
            listener(info.id)
        }

        stockId?.text = info.id
        stockCompanyName?.text = info.name
        stockCompanyPrice?.text = info.price.format(numberFormatter)
        stockCompanyTypes?.removeAllViews().also {
            info.companyType.map{ type ->
                Chip(itemView.context).also {
                    it.text = type.name
                }
            }.forEach(stockCompanyTypes::addView)
        }

        info.details.map { it.imageUrl }.ifPresent {
            val uri = Uri.parse(it.toString())
            stockCompanyPicture?.let {
                glide.load(uri).into(it)
            }
        }

        stockCompanyChangeValue?.text = info.priceChange.format(percentageFormatter)
        stockCompanyChangeIndicator?.setBackgroundResource(if (info.priceChange.isNegative())
            R.drawable.stock_change_arrow_down else R.drawable.stock_change_arrow_up
        )
    }
}

class RecyclerViewAdapter constructor(
    private val priceFomatter: NumberFormat,
    private val percentageFormatter: NumberFormat,
    private val glide: RequestManager,
    private val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {
    var stockInfoList: List<StockInfo> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.stock_list_item, parent, false)
        return RecyclerViewViewHolder(itemView, priceFomatter, percentageFormatter, glide, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(stockInfoList[position])
    }

    override fun getItemCount(): Int {
        return stockInfoList.size
    }
}