package com.mcarving.stocktracker.stockDetail

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView

interface StockDetailContract {
    interface View : BaseView<Presenter>{
        fun showLoadingIndicator(active : Boolean)
        fun showLatestPrice()
        fun showChange()
        fun showChangePercent()
        fun showStockDeleted()

    }

    interface Presenter : BasePresenter {
        fun deleteStock()
    }
}