package com.yum.stockapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.repository.StockInfoRepository
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repo: StockInfoRepository): ViewModel() {
    fun getStockInfo(id: String): LiveData<StockInfo> = repo.getStockInfo(id).toLiveData()
}