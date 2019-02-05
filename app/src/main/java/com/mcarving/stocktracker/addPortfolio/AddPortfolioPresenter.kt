package com.mcarving.stocktracker.addPortfolio

import android.content.Context
import android.util.Log
import com.mcarving.stocktracker.data.source.local.PortfolioSharedPreferences

class AddPortfolioPresenter constructor(
    private val mContext : Context,
   private val mAddPortfolioView : AddPortfolioContract.View
): AddPortfolioContract.Presenter {

    init {
        mAddPortfolioView.setPresenter(this)
    }

    override fun start() {
        // Not implemented
    }

    override fun savePortfolio(name: String) {

        PortfolioSharedPreferences(context = mContext).addPortfolioName(name)

        // load portfolio names from shared preferences
        val portfolioNames : List<String> = PortfolioSharedPreferences(mContext)
            .getPortfolioNames()

        val TAG = "AddPortfolioPresenter"
        Log.d(TAG, "savePortfolio: portfolioNames.size = " + portfolioNames.size)
    }


}