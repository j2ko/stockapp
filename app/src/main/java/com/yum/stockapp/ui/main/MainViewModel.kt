package com.yum.stockapp.ui.main

import androidx.lifecycle.*
import com.yum.stockapp.data.model.StockCompanyType
import com.yum.stockapp.data.model.StockFilter
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.repository.FilterRepository
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.utils.SingleLiveEvent
import com.yum.stockapp.utils.toLiveData
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val infoRepo: StockInfoRepository,
    private val filterRepo: FilterRepository,
) : ViewModel() {
    private val navigator = SingleLiveEvent<String>()
    private val filter = MutableLiveData<StockFilter>()

    fun getStockInfo(): LiveData<List<StockInfo>> = infoRepo.getStockInfoList().toLiveData()
    fun getCompanyTypes(): LiveData<Set<StockCompanyType>> = infoRepo.getStockInfoList().map {
        it.flatMap { it.companyType }.toSet()
    }.toLiveData()

    fun getFilter(): LiveData<StockFilter> = filter
    fun setFilter(name: String, companyTypes: Set<StockCompanyType>) = filter.postValue(StockFilter(name, companyTypes))

    fun navigateToDetails(id: String) {
        navigator.value = id
    }

    fun naviageDetailsEvent() = navigator
}


