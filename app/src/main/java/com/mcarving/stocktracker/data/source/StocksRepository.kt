package com.mcarving.stocktracker.data.source

import android.support.annotation.NonNull
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource

class StocksRepository private constructor(
    private val mStocksLocalDataSource : StocksDataSource,
    private val mStocksRemoteDataSource: StocksDataSource
    ): StocksDataSource{

     val mCachedStocks : MutableMap<String, Stock> = mutableMapOf()
     var mCacheIsDirty = false

    override fun getStocksByPortfolio(portfolio: String, callback: StocksDataSource.LoadStocksCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStock(symbol: String, callback: StocksDataSource.GetStockCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun refreshStock(stock: Stock) {
        mCacheIsDirty = true
    }

    override fun refreshStockList(updatedStocks: List<Stock>) {
        mCacheIsDirty = true
    }

    override fun saveStock(stock: Stock, portfolio: String) {

        mStocksLocalDataSource.saveStock(stock)
        //mStocksRemoteDataSource.saveStock(stock)

        //Do in memory cache update to keep the app UI up to date
        mCachedStocks[stock.symbol] = stock
    }

    override fun deleteStocksByPortfolio(portfolio: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Used to force {@link #getInstance(StocksLocalDataSource, StocksRemoteDataSource)} to create a new instance
     * next time it's called.
     */

    override fun deleteStock(symbol: String, porttfolio: String) {

        mStocksLocalDataSource.deleteStock(symbol, porttfolio)

        mCachedStocks.remove(symbol)
    }

    fun refreshCache(stocks : List<Stock>){
        mCachedStocks.clear()

        stocks.forEach {
            stock ->
            mCachedStocks[stock.symbol] = stock
        }

        mCacheIsDirty = false
    }

    companion object {
        @Volatile private var INSTANCE : StocksRepository? = null

        fun getInstance(@NonNull stockslocalDataSource : StocksDataSource,
                        @NonNull stocksRemoteDataSource: StocksDataSource) : StocksRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: StocksRepository(stockslocalDataSource, stocksRemoteDataSource).also { INSTANCE = it }
            }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}