package com.mcarving.stocktracker.addPortfolio

import android.content.Context
import android.util.Log
import com.mcarving.stocktracker.R
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
        // load portfolio names from shared preferences
        val portfolioNames : List<String> = PortfolioSharedPreferences(mContext)
            .getPortfolioNames()

        when {
            portfolioNames.contains(name) -> mAddPortfolioView.showSaveError("Portfolio: $name already exists")
            name == "" -> mAddPortfolioView.showSaveError(mContext.getString(R.string.empty_portfolio_message))
            else -> //save portfolio name to SharedPreferences
                PortfolioSharedPreferences(context = mContext).addPortfolioName(name)
        }
    }
}