package com.mcarving.stocktracker.data.source

import android.support.annotation.NonNull
import com.mcarving.stocktracker.data.Stock

interface StocksDataSource {

    interface LoadStocksCallback {

        fun onStocksLoaded(stocks : List<Stock>)

        fun onDataNotAvailable()
    }

    interface GetStockCallback {

        fun onStockLoaded(stock : Stock)

        fun onDataNotAvailable()
    }

    fun getStocksByPortfolio(@NonNull portfolio : String, @NonNull callback : LoadStocksCallback)

    fun getStock(@NonNull symbol : String, @NonNull callback: GetStockCallback)

    fun saveStock(@NonNull stock : Stock)

    fun refreshStock(@NonNull stock : Stock)
    fun refreshStockList(@NonNull stocks : List<Stock>)


    fun deleteStocksByPortfolio(@NonNull portfolio : String)

    fun deleteStock(@NonNull symbol: String)
}