package com.mcarving.stocktracker.stocks

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Stock

interface StocksContract {

    interface View : BaseView<Presenter>{
        fun showStocks(stocks : List<Stock>)

        fun showAddStock()

        fun showStockDetailUI(symbol : String)

        fun showLoadingStockError()

        fun showNoStocks()

        fun setTitle()
    }

    interface Presenter : BasePresenter {
        fun result(requestCode : Int, resultCode: Int)

        fun loadStocks()

        fun addNewStock()

        fun openStockDetails(symbol : String)
    }
}