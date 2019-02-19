package com.mcarving.stocktracker.addPortfolio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import com.mcarving.stocktracker.data.source.local.StocksLocalDataSource
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource
import com.mcarving.stocktracker.util.AppExecutors
import com.mcarving.stocktracker.util.NetworkHelper
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

        val stocksDao = StocksDatabase.getDatabase(applicationContext).stockDao()
        val portfolioDao = StocksDatabase.getDatabase(applicationContext).portfolioDao()

        val retrofitRquest  = Retrofit.Builder()
            .baseUrl(StocksRemoteDataSource.BASE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        AddPortfolioPresenter(this, mFragment,
            StocksRepository.getInstance(
                NetworkHelper.getInstance(),
                StocksLocalDataSource.getInstance(AppExecutors(), stocksDao, portfolioDao),
                StocksRemoteDataSource.getInstance(AppExecutors(), portfolioDao, retrofitRquest))
            )
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
