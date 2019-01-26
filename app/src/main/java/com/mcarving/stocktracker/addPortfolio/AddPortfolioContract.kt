package com.mcarving.stocktracker.addPortfolio

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView

interface AddPortfolioContract {

    interface View : BaseView<Presenter> {
        fun showEmptyPortfolioError()

        fun showPortfoliosList()

    }

    interface Presenter : BasePresenter {
        fun savePortfolio(name : String)
    }

}