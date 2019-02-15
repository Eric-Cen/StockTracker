package com.mcarving.stocktracker.data.source.local

import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.VisibleForTesting
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.util.AppExecutors

open class StocksLocalDataSource private constructor(
    private val mAppExecutors: AppExecutors,
    private val mStocksDao: StocksDao
) : StocksDataSource {

    override fun getStocksByPortfolio(context: Context,
                                      portfolioName: String,
                                      callback: StocksDataSource.LoadStocksCallback) {

        val symbols : List<String>? = PortfolioSharedPreferences(context = context)
            .getSymbolsFromPortfolio(portfolioName)

        val runnable = Runnable {

            val stocks: MutableList<Stock> = mutableListOf()

            symbols?.let { stockSymbols ->
                stockSymbols.forEach { symbol ->

                    val stock: Stock? = mStocksDao.getStockBySymbol(symbol)
                    stock?.let {
                        stocks.add(it)

                    }

                }
            }
            mAppExecutors.mainThread().execute {
                if (stocks.size >= 1) {
                    callback.onStocksLoaded(stocks)
                } else {
                    callback.onDataNotAvailable()
                }
            }

        }
        mAppExecutors.diskIO().execute(runnable)
    }

    override fun getStock(context: Context,
                          symbol: String,
                          callback: StocksDataSource.GetStockCallback) {

        val runnable = Runnable {
            val stock: Stock? = mStocksDao.getStockBySymbol(symbol)


            mAppExecutors.mainThread().execute {
                if (stock != null) {
                    callback.onStockLoaded(stock)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }

        mAppExecutors.diskIO().execute(runnable)
    }



    override fun refreshStock(stock: Stock) {
        val updateRunnable = Runnable {
            mStocksDao.update(stock)
        }
        mAppExecutors.diskIO().execute(updateRunnable)
    }


    override fun refreshStocks(updatedStocks: List<Stock>) {
        val updateRunnable = Runnable {
            updatedStocks.forEach { stock ->
                mStocksDao.update(stock)
            }
        }
        mAppExecutors.diskIO().execute(updateRunnable)
    }

    override fun saveStock(context: Context, stock: Stock, portfolioName: String) {
        val saveRunnable = Runnable {
            mStocksDao.insert(stock)
        }
        mAppExecutors.diskIO().execute(saveRunnable)

        PortfolioSharedPreferences(context = context).addSymbolToPortfolio(stock.symbol, portfolioName)

    }

    override fun deletePortfolio(context: Context, portfolioName: String) {
        // get stock symbols for the specified portfolio from SharedPreferences
        val symbols: List<String>? = PortfolioSharedPreferences(context = context)
            .getSymbolsFromPortfolio(portfolioName)
        PortfolioSharedPreferences(context).removePortfolio(portfolioName)

        val deleteRunnable = Runnable {
            symbols?.let { stockSymbols ->
                stockSymbols.forEach { stockSymbol ->

                    mStocksDao.deleteStockBySymbol(stockSymbol)
                }
            }
        }
        mAppExecutors.diskIO().execute(deleteRunnable)

    }

    //val searchBy = "%" + portfolio + "%"
    //val stocksByPortfolio = mStocksDao.getStocksByPortfolio(searchBy)

    // compare portfolio column with portfolio,
//            val (updateList, deleteList) =
//                    stocksByPortfolio.partition { it.byPortfolio.length > portfolio.length }
//
//            // if portfolio column has only one portfolio, delete the stock
//            deleteList.forEach{
//                mStocksDao.deleteStockBySymbol(it.symbol)
//            }
//
//            // if portfolio column has more, remove portfolio from the column, update stock
//
//            updateList.forEach {
//                val beforeComma = portfolio + ","
//                val afterComma = "," + portfolio
//
//                if(it.byPortfolio.contains(beforeComma, true)){
//                    it.byPortfolio = it.byPortfolio.replace(beforeComma, "")
//
//                } else if(it.byPortfolio.contains(afterComma, true)){
//
//                    it.byPortfolio = it.byPortfolio.replace(afterComma, "")
//                }
//                mStocksDao.update(it)
//            }


    override fun deleteStock(context: Context, symbol: String, portfolioName : String) {
        val deleteRunnable = Runnable {
            mStocksDao.deleteStockBySymbol(symbol)
        }

        mAppExecutors.diskIO().execute(deleteRunnable)

        // remove stock symbol from portfolio in SharedPreferences
        PortfolioSharedPreferences(context = context)
            .removeSymbolFromPortfolio(symbol, portfolioName)
    }


    companion object {
        @Volatile
        private var INSTANCE: StocksLocalDataSource? = null

        fun getInstance(

            @NonNull appExecutors: AppExecutors,
            @NonNull stocksDao: StocksDao
        ): StocksLocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StocksLocalDataSource(appExecutors, stocksDao).also { INSTANCE = it }
            }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}