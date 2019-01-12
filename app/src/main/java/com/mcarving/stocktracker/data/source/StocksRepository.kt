package com.mcarving.stocktracker.data.source

import android.support.annotation.NonNull
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource

class StocksRepository private constructor(
    private val mStocksLocalDataSource : StocksDataSource,
    private val mStocksRemoteDataSource: StocksDataSource
    ): StocksDataSource{

    override fun getStocksByPortfolio(portfolio: String, callback: StocksDataSource.LoadStocksCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStock(symbol: String, callback: StocksDataSource.GetStockCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveStock(stock: Stock) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshStock(stock: Stock) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshStockList(stocks: List<Stock>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteStocksByPortfolio(portfolio: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteStock(symbol: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @Volatile private var INSTANCE : StocksRepository? = null

        fun getInstance(@NonNull stockslocalDataSource : StocksDataSource,
                        @NonNull stocksRemoteDataSource: StocksDataSource) : StocksRepository =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: StocksRepository(stockslocalDataSource, stocksRemoteDataSource).also { INSTANCE = it }
        }
    }
}