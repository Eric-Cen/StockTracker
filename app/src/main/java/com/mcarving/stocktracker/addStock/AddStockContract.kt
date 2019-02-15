package com.mcarving.stocktracker.addStock

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Stock

interface AddStockContract {
    interface View : BaseView<Presenter>{

        fun showInputError()

        fun showStockError()

        fun showStock(stock : Stock)

        fun showStocksList(isStockAdded : Boolean)

        fun showSaveError()
    }

    interface Presenter : BasePresenter {
        fun saveStock()

        fun loadStock(symbol : String)
    }
}