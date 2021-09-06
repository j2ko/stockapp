package com.yum.stockapp.ui.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yum.stockapp.R
import com.yum.stockapp.ui.main.stocklist.RecyclerViewAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var recyclerView : RecyclerView

    @Inject
    lateinit var test: String

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        Log.e("TAG", test)
        recyclerView = findViewById(R.id.stockList)
        recyclerView.adapter = RecyclerViewAdapter(mainViewModel, Glide.with(this))
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
    }
}