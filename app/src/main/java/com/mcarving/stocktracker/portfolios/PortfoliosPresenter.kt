package com.mcarving.stocktracker.portfolios

import android.app.Activity
import android.content.Context
import com.mcarving.stocktracker.addPortfolio.AddPortfolioActivity
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.local.PortfolioSharedPreferences

class PortfoliosPresenter constructor(
    private val mContext : Context,
    private val mStocksRepository: StocksRepository,
    private val mPortfoliosView: PortfoliosContract.View
) : PortfoliosContract.Presenter {

    init {
        mPortfoliosView.setPresenter(this)
    }

    override fun start() {
        loadPortfolios()
    }

    override fun result(requestCode: Int, resultCode: Int) {

        //if a portfolio was successfully added, show ???
        if(AddPortfolioActivity.REQUEST_ADD_PORTFOLIO == requestCode
        && Activity.RESULT_OK == resultCode){
            // TODO show something?
        }
    }

    override fun loadPortfolios() {
        // load portfolio names from shared preferences
        val portfolioNames : List<String> = PortfolioSharedPreferences(mContext)
            .getPortfolioNames()
        if(portfolioNames.isEmpty()){
            mPortfoliosView.showNoPortfolios()
        } else {
            mPortfoliosView.showPortfolios(portfolioNames)
        }
    }


    override fun addNewPortfolio() {
        mPortfoliosView.showAddPortfolio()
    }

    override fun openPortfolioDetails(requestedPortfolio: String) {
        mPortfoliosView.showPortfolioDetailUi(requestedPortfolio)
    }


}
