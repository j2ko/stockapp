package com.yum.stockapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.toLiveData
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.data.repository.StockInfoRepositoryImpl
import com.yum.stockapp.ui.base.BaseViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(val repo: StockInfoRepository): BaseViewModel<DetailsNavigator>() {
    fun getStockInfo(id: String): LiveData<StockInfo> = repo.getStockInfo(id).toLiveData()
}