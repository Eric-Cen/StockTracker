package com.mcarving.stocktracker.stocks

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Stock

interface StocksContract {

    interface View : BaseView<Presenter>{
        fun showStocks(stocks : List<Stock>)

        fun showAddStock()

        fun showStockDetailUI(symbol : String)

        fun showPortfolioUI(portfolio : String)

        fun showPortfolioList()

        fun showLoadingStockError()

        fun showNoStocks()

        fun setTitle(title : String)
    }

    interface Presenter : BasePresenter {
        fun result(requestCode : Int, resultCode: Int)

        fun loadStocks()

        fun addNewStock()

        fun openStockDetails(symbol : String)

        fun openPortfolio(portfolio : String)

        fun openPortfolioList()
    }
}