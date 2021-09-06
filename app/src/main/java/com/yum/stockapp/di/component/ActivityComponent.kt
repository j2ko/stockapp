package com.yum.stockapp.di.component

import com.yum.stockapp.ui.details.DetailsActivity
import com.yum.stockapp.ui.main.MainActivity
import dagger.Component

@Component(
    modules = [

    ]
)
interface ActivityComponent {
    fun inject (activity: MainActivity)

    fun inject (activity: DetailsActivity)
}