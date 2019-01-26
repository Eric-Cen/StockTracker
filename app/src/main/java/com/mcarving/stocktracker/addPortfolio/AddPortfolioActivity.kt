package com.mcarving.stocktracker.addPortfolio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.mcarving.stocktracker.R

class AddPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_portfolio)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setTitle("New Portfolio")
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        if(null == savedInstanceState){
            initFragment(AddPortfolioFragment.newInstance())
        }
    }

    private fun initFragment(detailFragment : Fragment){
        // Add the AddPortfolioFragment to the layout
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contentFrame, detailFragment)
        transaction.commit()
    }


    companion object {
        const val REQUEST_ADD_PORTFOLIO = 101
    }
}
