package com.mcarving.stocktracker.portfolios

import android.app.Activity
import android.content.Context
import android.util.Log
import com.mcarving.stocktracker.addPortfolio.AddPortfolioActivity
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.local.PortfolioSharedPreferences
import com.mcarving.stocktracker.mock.TestData

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

        val strList = TestData.portfolioTestData()

        val TAG = "PortfoliosPresenter"

        Log.d(TAG, "onCreate: strList.size() = " + strList.size)

        if(strList.isEmpty()){
        //if(portfolioNames.isEmpty()){

            mPortfoliosView.showNoPortfolios()
        } else {
            Log.d(TAG, "loadPortfolios: loading portfolio names")
            //mPortfoliosView.showPortfolios(portfolioNames)
            mPortfoliosView.showPortfolios(strList)
        }
    }


    override fun addNewPortfolio() {
        mPortfoliosView.showAddPortfolio()
    }

    override fun openPortfolioDetails(requestedPortfolio: String) {
        mPortfoliosView.showPortfolioDetailUi(requestedPortfolio)
    }


}
