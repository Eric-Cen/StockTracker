package com.mcarving.stocktracker.portfolios

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.addPortfolio.AddPortfolioActivity
import com.mcarving.stocktracker.util.Utils
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.api.PortfolioResponse
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import com.mcarving.stocktracker.data.source.local.StocksLocalDataSource
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource
import com.mcarving.stocktracker.mock.TestData
import com.mcarving.stocktracker.util.AppExecutors
import com.mcarving.stocktracker.util.NetworkHelper
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PortfoliosActivity : AppCompatActivity() {

    private val TAG = "PortfoliosActivity"

    private lateinit var mPortfoliosPresenter: PortfoliosPresenter

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var viewListener : PortfoliosContract.View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolios)

        var portfoliosFragment = supportFragmentManager
            .findFragmentById(R.id.contentFrame)

        if(portfoliosFragment == null) {
            portfoliosFragment = PortfoliosFragment.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.contentFrame, portfoliosFragment)
            transaction.commit()
        }

        if( portfoliosFragment is PortfoliosContract.View ){
            viewListener = portfoliosFragment
        }


        val portfolioDao = StocksDatabase.getDatabase(applicationContext).portfolioDao()
        val stocksDao = StocksDatabase.getDatabase(applicationContext).stockDao()
        val purchaseDao = StocksDatabase.getDatabase(applicationContext).purchaseDao()

        val retrofitRquest  = Retrofit.Builder()
            .baseUrl(StocksRemoteDataSource.BASE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val repository = StocksRepository.getInstance(
            NetworkHelper.getInstance(),
            StocksLocalDataSource.getInstance(AppExecutors(), stocksDao, portfolioDao, purchaseDao),
            StocksRemoteDataSource.getInstance(AppExecutors(), portfolioDao, retrofitRquest))

        mPortfoliosPresenter = PortfoliosPresenter(applicationContext,
            repository,
            viewListener)

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
