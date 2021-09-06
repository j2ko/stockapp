package com.yum.stockapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yum.stockapp.R
import com.yum.stockapp.ui.main.stocklist.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        recyclerView = findViewById(R.id.stockList)
        recyclerView.adapter = RecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
    }
}