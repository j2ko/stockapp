package com.yum.stockapp.ui.main.stocklist

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.yum.stockapp.R
import com.yum.stockapp.data.model.StockDetails
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.ui.main.MainViewModel
import java.text.NumberFormat
import java.util.*

class RecyclerViewViewHolder(v : View, private val glide : RequestManager): RecyclerView.ViewHolder(v) {
    private var stockId : TextView? = v.findViewById(R.id.stockId)
    private var stockCompanyPicture: ImageView? = v.findViewById(R.id.stockCompanyLogo)
    private var stockCompanyName : TextView? = v.findViewById(R.id.stockCompanyName)
    private var stockCompanyPrice : TextView? = v.findViewById(R.id.stockCompanyPrice)
    private var stockCompanyChangeIndicator : ImageView? = v.findViewById(R.id.stockCompanyChangeIndicator)
    private var stockCompanyChangeValue : TextView? = v.findViewById(R.id.stockCompanyChangeValue)
    private var numberFormatter : NumberFormat = NumberFormat.getInstance(Locale.US)
    private var percentageFormatter : NumberFormat = NumberFormat.getPercentInstance(Locale.US)

    fun bind(info: StockInfo) {
        itemView.setOnClickListener{
            TODO("navigate to details screen")
        }

        stockId?.text = info.id
        stockCompanyName?.text = info.name
        stockCompanyPrice?.text = info.price.format(numberFormatter)

        info.details.map { it.imageUrl }.ifPresent {
            var uri = Uri.parse(it.toString())
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

class RecyclerViewAdapter constructor(private val viewModel: MainViewModel, private val glide: RequestManager): RecyclerView.Adapter<RecyclerViewViewHolder>() {
    var stockInfoList : List<StockInfo> = Collections.emptyList()
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            viewModel.getStockInfo().observe(viewModel.activity, {
                stockInfoList = it
                notifyDataSetChanged()
            })
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.stock_list_item, parent, false)
        return RecyclerViewViewHolder(itemView, glide)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(stockInfoList[position])
    }

    override fun getItemCount(): Int {
        return stockInfoList.size
    }
}