package com.mcarving.stocktracker.stocks

import android.app.Activity
import android.content.Context
import com.mcarving.stocktracker.addStock.AddStockActivity
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.util.Utils
import okhttp3.internal.Util

class StocksPresenter constructor(
    private val mContext : Context,
    private val mStocksRepository: StocksRepository,
    private val mStocksView : StocksContract.View,
    private val mPortfolioName : String
) : StocksContract.Presenter {

    init {
        mStocksView.setPresenter(this)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if(AddStockActivity.REQUEST_ADD_STOCK == requestCode
        && Activity.RESULT_OK == resultCode){
            Utils.showToastMessage(mContext, "StocksPresenter: to update stock list")
            loadStocks()
        } // else no need to load from the database
         else {
            Utils.showToastMessage(mContext, "StocksPresenter: no need to update stock list")
        }
    }

    override fun start() {
        loadStocks()
        mStocksView.setupDrawerContent()
        getPortfolioName()
    }

    override fun loadStocks() {

        // get stock symbols for the portfolio

        // load stocks from internet or local database,??

        // show in recyclerView

        mStocksRepository.getStocksByPortfolio(mContext, mPortfolioName,
            object : StocksDataSource.LoadStocksCallback{
                override fun onStocksLoaded(stocks: List<Stock>) {

                    mStocksView.showStocks(stocks)
                }

                override fun onDataNotAvailable() {

                    mStocksView.showLoadingStockError()
                }
            })

    }

    override fun addNewStock() {
        mStocksView.showAddStock()
    }

    override fun openStockDetails(symbol: String) {
        mStocksView.showStockDetailUI(symbol)
    }

    override fun openPortfolio(portfolio: String) {
        //TODO  get the stock list,
        // replace the data in adapter
        mStocksView.showPortfolioUI(portfolio)
    }

    override fun openPortfolioList() {
        mStocksView.showPortfolioList()
    }

    fun getPortfolioName() {
        var nameList = listOf<String>()
        mStocksRepository.getPortfolioNames(object : StocksDataSource.LoadPortfolioNamesCallback{
            override fun onPortfolioNamesLoaded(names: List<String>) {
                mStocksView.updateDrawerContent(names)
            }

            override fun onDataNotAvailable() {
                mStocksView.updateDrawerContent(listOf<String>())
            }
        })
    }
}