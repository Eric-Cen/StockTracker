package com.mcarving.stocktracker.portfolios

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.mock.TestData

class PortfoliosActivity : AppCompatActivity() {
    //TODO enable item click to start new activity,
    //TODO implement test for mainactvity?


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPortfolioAdapter: PortfolioAdapter
    private lateinit var mViewManager: RecyclerView.LayoutManager

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolios)

        val strList = TestData.portfolioTestData()


        val TAG = "PortfoliosActivity"
        Log.d(TAG, "onCreate: strList.size() = " + strList.size)

        mViewManager = LinearLayoutManager(this)
        mPortfolioAdapter = PortfolioAdapter(strList)

        mRecyclerView = findViewById<RecyclerView>(R.id.rv_portfolio).apply {
            setHasFixedSize(true)
        }
        mRecyclerView.layoutManager = mViewManager
        mRecyclerView.adapter = mPortfolioAdapter



        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // for example, swap UI fragments here
            true
        }

    }

}
