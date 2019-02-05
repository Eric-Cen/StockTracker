package com.mcarving.stocktracker.addStock

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Stock

interface AddStockContract {
    interface View : BaseView<Presenter>{
        fun showEmptyStockError()
        fun showStock(stock : Stock)

        fun showStocksList()
    }

    interface Presenter : BasePresenter {
        fun saveStock(stock : Stock)
    }
}