package com.yum.stockapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.toLiveData
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.data.repository.StockInfoRepositoryImpl

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private var infoRepository : StockInfoRepository = StockInfoRepositoryImpl()

    public fun getStockInfo(): LiveData<List<StockInfo>> = infoRepository.getStockInfoList().toLiveData()
}