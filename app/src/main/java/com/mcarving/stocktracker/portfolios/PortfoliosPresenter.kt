package com.mcarving.stocktracker.portfolios

import com.mcarving.stocktracker.data.source.StocksRepository

class PortfoliosPresenter : PortfoliosContract.Presenter {

    private lateinit var mStocksRepository : StocksRepository
    private lateinit var mPortfoliosView: PortfoliosContract.View

    override fun result(requestCode: Int, resultCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadPortfolios() {
        // load portfolio names from shared preferences


    }


    override fun addNewPortfolio() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openPortfolioDetails(requestedPortfolio: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {

        loadPortfolios(false)
    }
}
