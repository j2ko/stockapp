package com.yum.stockapp.utils

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData() =
    MutableLiveData<T>().apply {
        this@toLiveData.subscribe {
            value = it
        }
    }

