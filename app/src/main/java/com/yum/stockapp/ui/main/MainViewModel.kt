package com.yum.stockapp.ui.main;

import androidx.lifecycle.*
import com.yum.stockapp.data.model.StockCompanyType
import com.yum.stockapp.data.model.StockFilter
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.repository.FilterRepository
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.ui.base.BaseViewModel
import com.yum.stockapp.utils.toLiveData
import io.reactivex.Observable
import javax.inject.Inject

class MainViewModel @Inject constructor(val infoRepo: StockInfoRepository, val filterRepo: FilterRepository): BaseViewModel<MainNavigator>() {
    fun getStockInfo(): LiveData<List<StockInfo>> = infoRepo.getStockInfoList().toLiveData()
    fun getFilter(): LiveData<StockFilter> = filterRepo.getFilter().toLiveData()
    fun setFilter(name: String, companyTypes: Set<StockCompanyType>) = filterRepo.setFilter(StockFilter(name, companyTypes))
}


