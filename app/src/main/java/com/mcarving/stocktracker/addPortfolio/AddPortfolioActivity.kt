package com.mcarving.stocktracker.addPortfolio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mcarving.stocktracker.R

class AddPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_portfolio)
    }

    companion object {
        const val REQUEST_ADD_PORTFOLIO = 101
    }
}
