package com.mcarving.stocktracker.data.source.local

import android.support.annotation.NonNull
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.util.AppExecutors

open class StocksLocalDataSource private constructor(
    private val mAppExecutors : AppExecutors,
    private val mStocksDao: StocksDao
): StocksDataSource{


    override fun getStocks(callback: StocksDataSource.LoadStocksCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStock(symbol: String, callback: StocksDataSource.GetStockCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshStocks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllStocks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteStock(symbol: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        @Volatile private var INSTANCE : StocksLocalDataSource? = null

        fun getInstance(@NonNull appExecutors: AppExecutors,
                        @NonNull stocksDao: StocksDao) : StocksLocalDataSource =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: StocksLocalDataSource(appExecutors, stocksDao).also { INSTANCE = it }
                }
    }
}