package com.yum.stockapp.ui.main.stocklist

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yum.stockapp.R
import com.yum.stockapp.data.model.StockInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*

class RecyclerViewViewHolder(v : View): RecyclerView.ViewHolder(v) {
    private var stockId : TextView? = v.findViewById(R.id.stockId)
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
        stockCompanyChangeValue?.text = info.priceChange.format(percentageFormatter)
        stockCompanyChangeIndicator?.setBackgroundResource(if (info.priceChange.isNegative())
            R.drawable.stock_change_arrow_down else R.drawable.stock_change_arrow_up
        )
    }
}

class RecyclerViewAdapter constructor(private val stockInfoSource: Flowable<List<StockInfo>>): RecyclerView.Adapter<RecyclerViewViewHolder>() {
    var stockInfoList : List<StockInfo> = Collections.emptyList()
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        stockInfoSource.subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                stockInfoList = it
                notifyDataSetChanged()
            },{
                TODO("Implement error handling")
            })
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.stock_list_item, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(stockInfoList[position])
    }

    override fun getItemCount(): Int {
        return stockInfoList.size
    }
}