package com.mcarving.stocktracker.data.source

import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource
import com.mcarving.stocktracker.util.NetworkHelper

open class StocksRepository private constructor(
    private val mNetworkHelper: NetworkHelper,
    private val mStocksLocalDataSource: StocksDataSource,
    private val mStocksRemoteDataSource: StocksDataSource
) : StocksDataSource {


    /**
     * Gets stocks from remote data source if it is available,
     * else loads local data source
     */
    override fun getStocksByPortfolio(
        context: Context,
        portfolioName: String,
        callback: StocksDataSource.LoadStocksCallback) {
        if (mNetworkHelper.isNetworkAvailable(context)) {
            // get stock information from internet
            mStocksRemoteDataSource.getStocksByPortfolio(context, portfolioName,
                object : StocksDataSource.LoadStocksCallback {

                    override fun onStocksLoaded(stocks: List<Stock>) {

                        callback.onStocksLoaded(stocks)
                        // update stock list in local database
                        refreshStocks(stocks)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
        } else {
            // get stock information in local database
            mStocksLocalDataSource.getStocksByPortfolio(context, portfolioName, callback)
        }
    }

    override fun getStock(
        context: Context,
        symbol: String,
        callback: StocksDataSource.GetStockCallback) {

        val TAG = "StocksRepository"
        if (mNetworkHelper.isNetworkAvailable(context)) {
            Log.d(TAG, "getStock: loading from internet")
            // get stock information from internet
            mStocksRemoteDataSource.getStock(context, symbol, object : StocksDataSource.GetStockCallback {
                override fun onStockLoaded(stock: Stock) {
                    callback.onStockLoaded(stock)

                    // update informaiton for the stock in local database
                    refreshStock(stock)
                }

                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }
            })
        } else {
            Log.d(TAG, "getStock: loading from local database")
            // get stock information from local database
            mStocksLocalDataSource.getStock(context, symbol, callback)
        }
    }

    override fun refreshStock(stock: Stock) {
        mStocksLocalDataSource.refreshStock(stock)
    }

    override fun refreshStocks(updatedStocks: List<Stock>) {
        mStocksLocalDataSource.refreshStocks(updatedStocks)
    }

    override fun saveStock(context: Context, stock: Stock, portfolioName: String) {
        mStocksLocalDataSource.saveStock(context, stock, portfolioName)
    }

    override fun deletePortfolio(context: Context, portfolioName: String) {
        mStocksLocalDataSource.deletePortfolio(context, portfolioName)
    }

    /**
     * Used to force {@link #getInstance(StocksLocalDataSource, StocksRemoteDataSource)} to create a new instance
     * next time it's called.
     */
    override fun deleteStock(context: Context, symbol: String, portfolioName: String) {
        mStocksLocalDataSource.deleteStock(context, symbol, portfolioName)
    }

    companion object {
        @Volatile
        private var INSTANCE: StocksRepository? = null

        fun getInstance(
            @NonNull networkHelper: NetworkHelper,
            @NonNull stockslocalDataSource: StocksDataSource,
            @NonNull stocksRemoteDataSource: StocksDataSource
        ): StocksRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StocksRepository(networkHelper, stockslocalDataSource, stocksRemoteDataSource)
                    .also { INSTANCE = it }
            }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}