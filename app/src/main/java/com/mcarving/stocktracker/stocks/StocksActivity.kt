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
import com.mcarving.stocktracker.data.source.local.PortfolioSharedPreferences
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

        //viewListener = stocksFragment as StocksContract.View

        if(stocksFragment is StocksContract.View){
            viewListener = stocksFragment
        }

        val stocksDao = StocksDatabase.getDatabase(applicationContext).stockDao()

        val retrofitRquest  = Retrofit.Builder()
            .baseUrl(StocksRemoteDataSource.BASE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        mStocksPresenter = StocksPresenter(applicationContext,
            StocksRepository.getInstance(
                NetworkHelper.getInstance(),
                StocksLocalDataSource.getInstance(AppExecutors(), stocksDao),
                StocksRemoteDataSource.getInstance(AppExecutors(), retrofitRquest)
            ),
            viewListener, mPortfolioName)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        setupDrawerContent()
    }

    override fun onResume() {
        super.onResume()
        updateDrawerContent()
    }


    private fun updateDrawerContent() {
        val navigationView : NavigationView? = findViewById<NavigationView>(R.id.nav_view_stocks)

        val menu : Menu? = navigationView?.menu

        val portfolioNames : List<String> = PortfolioSharedPreferences(applicationContext)
            .getPortfolioNames()

        val TAG = "StocksActivity"
        Log.d(TAG, "updateDrawerContent: portfolioNames.zie = " + portfolioNames.size)

        val loops = if(portfolioNames.size > 7) 7 else portfolioNames.size

        for(i in 1..loops){
            val menuItemId = "nav_item".plus(i)

            val id : Int = resources.getIdentifier(menuItemId, "id", packageName)

            val navItem : MenuItem? = menu?.findItem(id)
            Log.d(TAG, "updateDrawerContent: portfolioName" + i + " = " + portfolioNames[i-1])
            if(i<= portfolioNames.size) {
                navItem?.apply {
                    title = portfolioNames[i - 1]
                    Log.d(TAG, "updateDrawerContent: in if portfolioName" + i + " = " + title)
                }
            }
        }
    }

    private fun setupDrawerContent(){

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navigationView = findViewById<NavigationView>(R.id.nav_view_stocks)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // for example, swap UI fragments here
            when(menuItem.itemId){
                //TODO get the Portfolio name from the clicked item, and load stocks from the portfolio
                R.id.nav_item1 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }
                R.id.nav_item2 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }
                R.id.nav_item3 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }
                R.id.nav_item4 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }
                R.id.nav_item5 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }
                R.id.nav_item6 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }
                R.id.nav_item7 -> {
                    Utils.showToastMessage(this, menuItem.toString())
                    mStocksPresenter.openPortfolio(menuItem.toString())
                }

                R.id.nav_portfolios -> {
                    mStocksPresenter.openPortfolioList()
                }
                else -> { // Note the block
                    Utils.showToastMessage(this, "other is selected")
                }
            }
            true
        }
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