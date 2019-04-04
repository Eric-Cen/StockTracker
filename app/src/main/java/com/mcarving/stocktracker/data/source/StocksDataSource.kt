package com.mcarving.stocktracker.data.source

import android.content.Context
import android.support.annotation.NonNull
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.data.Purchase
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

    interface LoadPortfolioNamesCallback {
        fun onPortfolioNamesLoaded(names : List<String>)

        fun onDataNotAvailable()
    }

    interface loadPurchaseCallback {
        fun onPurchaseLoad(purchase : Purchase)
        fun onDataNotAvailable()
    }

    fun getStocksByPortfolio(@NonNull context: Context, @NonNull portfolioName: String, @NonNull callback : LoadStocksCallback)

    fun getStock(@NonNull context: Context, @NonNull symbol : String, @NonNull callback: GetStockCallback)

    fun refreshStock(@NonNull stock: Stock)

    fun refreshStocks(@NonNull updatedStocks: List<Stock>)

    fun saveStock(@NonNull stock: Stock, @NonNull portfolioName : String)

    fun savePortfolio(@NonNull portfolio : Portfolio)

    fun getPortfolioNames(@NonNull callback: LoadPortfolioNamesCallback)

    fun deletePortfolio(@NonNull portfolioName: String)

    fun deleteStock(@NonNull symbol: String, @NonNull portfolioName : String)

    fun savePurchase(@NonNull purchase: Purchase)

    fun getPurchase(@NonNull symbol : String, @NonNull callback: loadPurchaseCallback)

    fun deletePurchase(@NonNull id : Int)

}