package com.mcarving.stocktracker.data.source

import android.support.annotation.NonNull
import com.mcarving.stocktracker.data.Stock

interface StocksDataSource {

    interface LoadStocksCallback {

        fun onStocksLoaded(stocks : List<Stock>)

        fun onDataNotAvailable();
    }

    interface GetStockCallback {

        fun onStockLoaded(stock : Stock)

        fun onDataNotAvailable()
    }

    fun getStocks(@NonNull callback : LoadStocksCallback)

    fun getStock(@NonNull symbol : String, @NonNull callback: GetStockCallback)

    fun refreshStocks()

    fun deleteAllStocks()

    fun deleteStock(@NonNull symbol: String)
}