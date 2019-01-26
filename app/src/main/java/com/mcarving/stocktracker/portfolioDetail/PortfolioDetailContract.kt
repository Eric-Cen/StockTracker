package com.mcarving.stocktracker.portfolioDetail

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Stock

interface PortfolioDetailContract {
    interface View : BaseView<Presenter> {
        fun setLoadingIndicator(active : Boolean)

        fun showPortfolioDeleted()

        fun showStockAdded()

        fun showStockDeleted()

        fun showStocks(stocks : List<Stock>)
    }

    interface Presenter : BasePresenter{
        fun deletePortfolio()

        fun addStock(stock : Stock)

        fun deleteStock(stock : Stock)

        fun loadStocks(forceUPdate : Boolean)

        fun result(requestCode : Int, resultCode : Int)
    }
}