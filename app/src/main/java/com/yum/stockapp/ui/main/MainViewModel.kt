package com.yum.stockapp.ui.main

import androidx.lifecycle.*
import com.yum.stockapp.data.model.StockCompanyType
import com.yum.stockapp.data.model.StockFilter
import com.yum.stockapp.data.model.StockInfo
import com.yum.stockapp.data.repository.StockInfoRepository
import com.yum.stockapp.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val infoRepo: StockInfoRepository,
) : ViewModel() {
    private val navigator = SingleLiveEvent<String>()
    private val filter = MutableLiveData<StockFilter>()
    private var searchByName = ""
    private var searchByType = emptySet<StockCompanyType>()

    fun getStockInfo(): LiveData<List<StockInfo>> = infoRepo.getStockInfoList().toLiveData()
    fun getFilterCompanyTypes(): LiveData<List<Pair<StockCompanyType, Boolean>>> = infoRepo
        .getStockInfoList()
        .map {
            it.flatMap { it.companyType }.toSet().map { Pair(it, searchByType.contains(it)) }
        }.toLiveData()

    fun getFilter(): LiveData<StockFilter> = filter

    fun setFilterName(name: String) {
        searchByName = name
        filter.postValue(StockFilter(searchByName, searchByType))
    }

    fun setFilterCompanyType(companyTypes: Set<StockCompanyType>) {
        searchByType = companyTypes
        filter.postValue(StockFilter(searchByName, searchByType))
    }

    fun navigateToDetails(id: String) {
        navigator.value = id
    }

    fun refreshFilter() {
        filter.postValue(StockFilter(searchByName, searchByType))
    }

    fun naviageDetailsEvent() = navigator
}


