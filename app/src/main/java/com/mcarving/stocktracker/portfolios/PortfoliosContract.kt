package com.mcarving.stocktracker.portfolios

import com.mcarving.stocktracker.BasePresenter
import com.mcarving.stocktracker.BaseView
import com.mcarving.stocktracker.data.Portfolio

interface PortfoliosContract {

    interface View : BaseView<Presenter>{
        fun showPortfolios(portfolios: List<String>)

        fun showAddPortfolio()

        fun showPortfolioDetailUi(portfolioName: String)

        fun showLoadingPortfolioError()

        fun showNoPortfolios()

        fun setTitle(title : String)

        fun updateDrawerContent(portfolioNames : List<String>)

        fun setupDrawerContent()
    }

    interface Presenter : BasePresenter {

        fun result(requestCode : Int, resultCode: Int)

        fun loadPortfolios()

        fun addNewPortfolio()

        fun openPortfolioDetails(requestedPortfolio: String)


    }
}
