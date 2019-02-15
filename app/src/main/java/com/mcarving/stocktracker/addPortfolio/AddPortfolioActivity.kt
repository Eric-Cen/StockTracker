package com.mcarving.stocktracker.addPortfolio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.mcarving.stocktracker.R

class AddPortfolioActivity : AppCompatActivity() {
    private lateinit var mFragment : AddPortfolioFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_portfolio)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            title = "New Portfolio"
            setDisplayHomeAsUpEnabled(true)
        }

        if(null == savedInstanceState){
            mFragment = AddPortfolioFragment.newInstance()
            initFragment(mFragment)
        }

        AddPortfolioPresenter(this, mFragment)
    }


    private fun initFragment(detailFragment : Fragment){
        // Add the AddPortfolioFragment to the layout
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contentFrame, detailFragment)
        transaction.commit()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        const val REQUEST_ADD_PORTFOLIO = 101
    }
}
