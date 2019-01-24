package com.mcarving.stocktracker.portfolioDetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mcarving.stocktracker.R

/** activity to show list of stocks in a portfolio
 *  to rename a portfolio, to delete portfolio
 *  to add/remove stocks for the portfolio
 */
class PortfolioDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio_detail)
    }
}
