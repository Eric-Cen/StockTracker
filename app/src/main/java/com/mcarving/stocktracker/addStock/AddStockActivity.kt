package com.mcarving.stocktracker.addStock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import com.mcarving.stocktracker.data.source.local.StocksLocalDataSource
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource
import com.mcarving.stocktracker.stocks.StocksFragment
import com.mcarving.stocktracker.util.AppExecutors
import com.mcarving.stocktracker.util.NetworkHelper
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class AddStockActivity : AppCompatActivity() {

    private lateinit var mFragment: AddStockFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            title = "Add Stock"
            setDisplayHomeAsUpEnabled(true)
        }

        if(null == savedInstanceState){
            mFragment = AddStockFragment.newInstance()
            initFragment(mFragment)
        }

        val portFolioName = intent.getStringExtra(StocksFragment.PORTFOLIO_NAME_EXTRA)

        val stocksDao = StocksDatabase.getDatabase(applicationContext).stockDao()
        val retrofitRquest  = Retrofit.Builder()
            .baseUrl(StocksRemoteDataSource.BASE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        AddStockPresenter(this, mFragment, portFolioName,
            StocksRepository.getInstance(
                NetworkHelper.getInstance(),
                StocksLocalDataSource.getInstance(AppExecutors(), stocksDao),
                StocksRemoteDataSource.getInstance(AppExecutors(), retrofitRquest))
        )
    }

    private fun initFragment(addStockFragment : AddStockFragment){
        // Add the AddStockFragment to the layout
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.content_add_stock, addStockFragment)
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
        const val REQUEST_ADD_STOCK = 111
    }


}
