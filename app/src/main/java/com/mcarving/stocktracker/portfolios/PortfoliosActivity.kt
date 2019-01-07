package com.mcarving.stocktracker.portfolios

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.Utils
import com.mcarving.stocktracker.mock.TestData

class PortfoliosActivity : AppCompatActivity() {
    //TODO enable item click to start new activity,
    //TODO implement test for mainactvity?


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPortfolioAdapter: PortfolioAdapter
    private lateinit var mViewManager: RecyclerView.LayoutManager

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolios)

        val strList = TestData.portfolioTestData()


        val TAG = "PortfoliosActivity"
        Log.d(TAG, "onCreate: strList.size() = " + strList.size)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }


        mViewManager = LinearLayoutManager(this)
        mPortfolioAdapter = PortfolioAdapter(strList)

        mRecyclerView = findViewById<RecyclerView>(R.id.rv_portfolio).apply {
            setHasFixedSize(true)
        }
        mRecyclerView.layoutManager = mViewManager
        mRecyclerView.adapter = mPortfolioAdapter

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        setUpNavigationView()
    }

    fun setUpNavigationView(){
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // for example, swap UI fragments here
            when(menuItem.itemId){
                R.id.nav_item1 -> Utils.showToastMessage(this, "Portfolio 1 is selected")
                R.id.nav_item2 -> Utils.showToastMessage(this, "Portfolio 2 is selected")
                R.id.nav_item3 -> Utils.showToastMessage(this, "Portfolio 3 is selected")
                R.id.nav_item4 -> Utils.showToastMessage(this, "Portfolio 4 is selected")
                R.id.nav_item5 -> Utils.showToastMessage(this, "Portfolio 5 is selected")
                R.id.nav_item6 -> Utils.showToastMessage(this, "Portfolio 6 is selected")
                R.id.nav_item7 -> Utils.showToastMessage(this, "Portfolio 7 is selected")

                else -> { // Note the block
                    print("other is selected")
                }
            }
            true
        }
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
