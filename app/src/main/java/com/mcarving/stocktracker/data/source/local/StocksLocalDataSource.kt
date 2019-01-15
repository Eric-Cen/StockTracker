package com.mcarving.stocktracker.data.source.local

import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.VisibleForTesting
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.util.AppExecutors

open class StocksLocalDataSource private constructor(
    private val context: Context,
    private val mAppExecutors: AppExecutors,
    private val mStocksDao: StocksDao
) : StocksDataSource {

    override fun getStocksByPortfolio(
        @NonNull portfolio: String,
        @NonNull callback: StocksDataSource.LoadStocksCallback
    ) {

        val symbols: List<String>? = PortfolioSharedPreferences(context = context)
            .getSymbolsFromPortfolio(portfolio)

        val runnable = Runnable {
            symbols?.let { stockSymbols ->
                val stocks: MutableList<Stock> = mutableListOf()

                stockSymbols.forEach { symbol ->

                    val stock = mStocksDao.getStockBySymbol(symbol)
                    stocks.add(stock)
                }

                mAppExecutors.mainThread().execute {
                    if (stocks != null) {
                        callback.onStocksLoaded(stocks)
                    } else {
                        callback.onDataNotAvailable()
                    }
                }
            }
        }

        mAppExecutors.diskIO().execute(runnable)
    }


    override fun getStock(@NonNull symbol: String,
                          @NonNull callback: StocksDataSource.GetStockCallback) {
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


    override fun refreshStock(@NonNull stock: Stock) {
        val updateRunnable = Runnable {
            mStocksDao.updateStock(stock)
        }
        mAppExecutors.diskIO().execute(updateRunnable)
    }


    override fun refreshStockList(@NonNull updatedStocks: List<Stock>) {
        val updateRunnable = Runnable {
            updatedStocks.forEach { stock ->
                mStocksDao.updateStock(stock)
            }
        }
        mAppExecutors.diskIO().execute(updateRunnable)
    }

    override fun saveStock(@NonNull stock: Stock, @NonNull portfolio: String) {
        checkNotNull(stock)
        val saveRunnable = Runnable {
            mStocksDao.insertStock(stock)
        }
        mAppExecutors.diskIO().execute(saveRunnable)

        // add stock symbol to portfolio saved in SharedPreferences
        PortfolioSharedPreferences(context = context).addSymbolToPortfolio(stock.symbol, portfolio)

    }

    override fun deleteStocksByPortfolio(@NonNull portfolio: String) {
        // get stock symbols for the specified portfolio from SharedPreferences
        val symbols: List<String>? = PortfolioSharedPreferences(context = context)
            .getSymbolsFromPortfolio(portfolio)


        val deleteRunnable = Runnable {

            symbols?.let {
                it.forEach { stockSymbol ->
                    mStocksDao.deleteStockBySymbol(stockSymbol)
                }

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
//                mStocksDao.updateStock(it)
//            }
        }


        mAppExecutors.diskIO().execute(deleteRunnable)

        PortfolioSharedPreferences(context).removePortfolio(portfolio)

    }

    override fun deleteStock(@NonNull symbol: String, @NonNull porttfolio: String) {

        val deleteRunnable = Runnable {
            mStocksDao.deleteStockBySymbol(symbol)
        }

        mAppExecutors.diskIO().execute(deleteRunnable)

        // remove stock symbol from portfolio in SharedPreferences
        PortfolioSharedPreferences(context = context)
            .removeSymbolFromPortfolio(symbol, porttfolio)
    }


    companion object {
        @Volatile
        private var INSTANCE: StocksLocalDataSource? = null

        fun getInstance(
            @NonNull context: Context,
            @NonNull appExecutors: AppExecutors,
            @NonNull stocksDao: StocksDao
        ): StocksLocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StocksLocalDataSource(context, appExecutors, stocksDao).also { INSTANCE = it }
            }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}