package com.mcarving.stocktracker.stockDetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mcarving.stocktracker.R

class StockDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_detail)
    }

    companion object {
        const val EXTRA_STOCK_SYMBOL = "SYMBOL"
    }
}
