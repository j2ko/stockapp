package com.yum.stockapp.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData() =
    MutableLiveData<T>().apply {
        this@toLiveData.subscribe {
            value = it
        }
    }

