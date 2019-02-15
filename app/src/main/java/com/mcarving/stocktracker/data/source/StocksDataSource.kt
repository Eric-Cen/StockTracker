package com.mcarving.stocktracker.data.source

import android.content.Context
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

    fun getStocksByPortfolio(@NonNull context: Context, @NonNull portfolioName: String, @NonNull callback : LoadStocksCallback)

    fun getStock(@NonNull context: Context, @NonNull symbol : String, @NonNull callback: GetStockCallback)

    fun refreshStock(@NonNull stock: Stock)

    fun refreshStocks(@NonNull updatedStocks: List<Stock>)

    fun saveStock(@NonNull context: Context, @NonNull stock: Stock, @NonNull portfolioName : String)

    fun deletePortfolio(@NonNull context: Context, @NonNull portfolioName: String)

    fun deleteStock(@NonNull context: Context, @NonNull symbol: String, @NonNull portfolioName : String)

}