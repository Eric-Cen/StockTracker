package com.mcarving.stocktracker.portfolios

import com.mcarving.stocktracker.data.Portfolio

interface PortfoliosContract {

    interface View {
        fun showPortfolios(portfolios: List<Portfolio>)

        fun showAddPortfolio()

        fun showPortfolioDetailUi(portfolioId: String)
    }

    interface UserActionsListener {
        fun loadPortfolios(forceUpdate: Boolean)

        fun addNewPortfolio()

        fun openPortfolioDetails(requestedPortfolio: Portfolio)
    }
}
