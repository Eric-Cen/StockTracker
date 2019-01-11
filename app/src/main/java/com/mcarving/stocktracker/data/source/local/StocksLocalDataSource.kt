package com.mcarving.stocktracker.data.source.local

import android.support.annotation.NonNull
import android.support.annotation.VisibleForTesting
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.util.AppExecutors

open class StocksLocalDataSource private constructor(
    private val mAppExecutors : AppExecutors,
    private val mStocksDao: StocksDao
): StocksDataSource{

    override fun getStocksByPortfolio(portfolio: String, callback: StocksDataSource.LoadStocksCallback) {

        val searchBy = "%" + portfolio + "%"
        val runnable = Runnable {
            val stocks : List<Stock>? = mStocksDao.getStocksByPortfolio(searchBy)

            mAppExecutors.mainThread().execute{
                if(stocks != null){
                    callback.onStocksLoaded(stocks)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }

        mAppExecutors.diskIO().execute(runnable)
    }


    override fun getStock(symbol: String, callback: StocksDataSource.GetStockCallback) {
        val runnable = Runnable {
            val stock : Stock? = mStocksDao.getStockBySymbol(symbol)

            mAppExecutors.mainThread().execute {
                if(stock != null){
                    callback.onStockLoaded(stock)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }

        mAppExecutors.diskIO().execute(runnable)
    }

    override fun saveStock(stock: Stock) {
        checkNotNull(stock)
        val saveRunnable = Runnable {
            mStocksDao.insertStock(stock)
        }

        mAppExecutors.diskIO().execute(saveRunnable)
    }

    override fun refreshStock(stock: Stock) {
        val updateRunnable = Runnable {
            mStocksDao.updateStock(stock)
        }

        mAppExecutors.diskIO().execute(updateRunnable)
    }

    override fun refreshStockList(stocks: List<Stock>) {
        stocks.forEach { refreshStock(it) }
    }

    override fun deleteStocksByPortfolio(portfolio: String) {

        // compare portfolio column with portfolio,


        val deleteRunnable = Runnable {
            val searchBy = "%" + portfolio + "%"
            val stocksByPortfolio = mStocksDao.getStocksByPortfolio(searchBy)

            // compare portfolio column with portfolio,
            val (updateList, deleteList) =
                    stocksByPortfolio.partition { it.byPortfolio.length > portfolio.length }

            // if portfolio column has only one portfolio, delete the stock
            deleteList.forEach{
                mStocksDao.deleteStockBySymbol(it.symbol)
            }

            // if portfolio column has more, remove portfolio from the column, update stock

            updateList.forEach {
                val beforeComma = portfolio + ","
                val afterComma = "," + portfolio

                if(it.byPortfolio.contains(beforeComma, true)){
                    it.byPortfolio = it.byPortfolio.replace(beforeComma, "")

                } else if(it.byPortfolio.contains(afterComma, true)){

                    it.byPortfolio = it.byPortfolio.replace(afterComma, "")
                }
                mStocksDao.updateStock(it)
            }
        }


        mAppExecutors.diskIO().execute(deleteRunnable)

    }

    override fun deleteStock(symbol: String) {

        val deleteRunnable = Runnable {
            mStocksDao.deleteStockBySymbol(symbol)
        }

        mAppExecutors.diskIO().execute (deleteRunnable)
    }


    companion object {
        @Volatile private var INSTANCE : StocksLocalDataSource? = null

        fun getInstance(@NonNull appExecutors: AppExecutors,
                        @NonNull stocksDao: StocksDao) : StocksLocalDataSource =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: StocksLocalDataSource(appExecutors, stocksDao).also { INSTANCE = it }
                }

        @VisibleForTesting
        fun clearInstance(){ INSTANCE = null}
    }
}