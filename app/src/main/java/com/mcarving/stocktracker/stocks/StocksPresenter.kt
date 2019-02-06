package com.mcarving.stocktracker.stocks

import android.app.Activity
import android.content.Context
import com.mcarving.stocktracker.addStock.AddStockActivity
import com.mcarving.stocktracker.data.source.StocksRepository

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
            loadStocks()
        }
    }

    override fun start() {
        loadStocks()
    }

    override fun loadStocks() {

        // get stock symbols for the portfolio

        // load stocks from internet or local database

        // show in recyclerView

    }

    override fun addNewStock() {
        mStocksView.showAddStock()
    }

    override fun openStockDetails(symbol: String) {
        mStocksView.showStockDetailUI(symbol)
    }

    override fun openPortfolio(portfolio: String) {
        mStocksView.showPortfolioUI(portfolio)
    }

    override fun openPortfolioList() {
        mStocksView.showPortfolioList()
    }
}