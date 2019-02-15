package com.mcarving.stocktracker.addPortfolio

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Stock

interface AddPortfolioContract {

    interface View : BaseView<Presenter> {
        fun showPortfoliosList()

        fun showSaveError(message : String)
    }

    interface Presenter : BasePresenter {
        fun savePortfolio(name : String)

    }

}