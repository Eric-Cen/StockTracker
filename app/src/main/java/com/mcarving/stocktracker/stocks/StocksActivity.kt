package com.mcarving.stocktracker.stocks

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import com.mcarving.stocktracker.data.source.local.StocksLocalDataSource
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource
import com.mcarving.stocktracker.portfolioDetail.PortfolioDetailActivity.Companion.EXTRA_PORTFOLIO_NAME
import com.mcarving.stocktracker.util.AppExecutors
import com.mcarving.stocktracker.util.NetworkHelper
import com.mcarving.stocktracker.util.Utils
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class StocksActivity : AppCompatActivity(){
    private lateinit var mPortfolioName : String

    private lateinit var mStocksPresenter : StocksPresenter

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var navigationView : NavigationView

    private lateinit var viewListener : StocksContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stocks)

        mPortfolioName = intent.getStringExtra(EXTRA_PORTFOLIO_NAME)

        var stocksFragment = supportFragmentManager
            .findFragmentById(R.id.stocksFrame)

        if(stocksFragment == null){
            stocksFragment = StocksFragment.newInstance(mPortfolioName)

            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.stocksFrame, stocksFragment)
            transaction.commit()
        }


        if(stocksFragment is StocksContract.View){
            viewListener = stocksFragment
        }

        val stocksDao = StocksDatabase.getDatabase(applicationContext).stockDao()
        val portfolioDao = StocksDatabase.getDatabase(applicationContext).portfolioDao()

        val retrofitRquest  = Retrofit.Builder()
            .baseUrl(StocksRemoteDataSource.BASE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        mStocksPresenter = StocksPresenter(applicationContext,
            StocksRepository.getInstance(
                NetworkHelper.getInstance(),
                StocksLocalDataSource.getInstance(AppExecutors(), stocksDao, portfolioDao),
                StocksRemoteDataSource.getInstance(AppExecutors(), portfolioDao, retrofitRquest)
            ),
            viewListener, mPortfolioName)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_PORTFOLIO_NAME = "portfolio"
    }
}