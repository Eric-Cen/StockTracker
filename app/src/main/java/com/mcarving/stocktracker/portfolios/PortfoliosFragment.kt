package com.mcarving.stocktracker.portfolios

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mcarving.stocktracker.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import com.mcarving.stocktracker.addPortfolio.AddPortfolioActivity
import com.mcarving.stocktracker.mock.TestData
import com.mcarving.stocktracker.portfolioDetail.PortfolioDetailActivity
import com.mcarving.stocktracker.stocks.StocksActivity
import com.mcarving.stocktracker.util.Utils
import okhttp3.internal.Util

class PortfoliosFragment : PortfoliosContract.View, Fragment() {

    private lateinit var mPresenter: PortfoliosContract.Presenter
    private lateinit var mPortfoiloAdapter: PortfolioAdapter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewManager: RecyclerView.LayoutManager

    private var mDrawerLayout: DrawerLayout? = null
    private var navigationView : NavigationView? = null

    private var mItemListener = object : PortfolioItemListener{
        override fun onPortfolioClick(portFolioName: String) {
            mPresenter.openPortfolioDetails(portFolioName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPortfoiloAdapter = PortfolioAdapter(listOf<String>(), mItemListener)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.result(requestCode, resultCode)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_portfolios, container, false)

        mViewManager = LinearLayoutManager(context)


        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val fab : FloatingActionButton? = activity?.findViewById<FloatingActionButton>(R.id.fab_add)

        fab?.apply {
            setOnClickListener {
                Utils.showToastMessage(context, "to start add portfolio")
                val intent = Intent(context, AddPortfolioActivity::class.java)
                startActivity(intent)
            }
        }




        mRecyclerView = root.findViewById<RecyclerView>(R.id.rv_portfolio).apply {
            setHasFixedSize(true)
        }
        mRecyclerView.layoutManager = mViewManager
        mRecyclerView.adapter = mPortfoiloAdapter

        setTitle("Portfolios")

        return root
    }

    override fun showPortfolios(portfolios: List<String>) {
        mPortfoiloAdapter.replaceData(portfolios)
    }

    override fun showAddPortfolio() {
        val intent = Intent(context, AddPortfolioActivity::class.java)
        startActivityForResult(intent, AddPortfolioActivity.REQUEST_ADD_PORTFOLIO)
    }

    override fun showPortfolioDetailUi(portfolioName: String) {
        val intent = Intent(context, StocksActivity::class.java)
        intent.putExtra(StocksActivity.EXTRA_PORTFOLIO_NAME, portfolioName)
        startActivity(intent)
    }

    override fun showLoadingPortfolioError() {
        Utils.showToastMessage(context, "failed to load portfolio list")
    }

    override fun showNoPortfolios() {
        Utils.showToastMessage(context, "There is no portfoio to show")
    }


    override fun setPresenter(presenter: PortfoliosContract.Presenter) {
        mPresenter = presenter
    }

    override fun setTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    override fun setupDrawerContent(){

        mDrawerLayout = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)

        navigationView = activity?.findViewById<NavigationView>(R.id.nav_view)
        navigationView?.apply {
            setNavigationItemSelectedListener { menuItem ->
                // set item as selected to persist highlight
                menuItem.isChecked = true
                // close drawer when item is tapped
                mDrawerLayout?.apply {
                    closeDrawers()
                }

                // Add code here to update the UI based on the item selected
                // for example, swap UI fragments here
                when (menuItem.itemId) {
                    //TODO get the Portfolio name from the clicked item, and load stocks from the portfolio
                    R.id.nav_item1 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_item2 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_item3 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_item4 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_item5 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_item6 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_item7 -> {
                        loadDetails(menuItem)
                    }
                    R.id.nav_portfolios -> {
                        //do nothing
                    }

                    else -> { // Note the block
                        Utils.showToastMessage(context, "other is selected")
                    }
                }
                true
            }


        }
    }

    private fun loadDetails(menutItem : MenuItem){
        Utils.showToastMessage(context, menutItem.toString())
        val name = menutItem.toString()
        if(name.isNotEmpty()) {
            mPresenter.openPortfolioDetails(name)
        }
    }

    override fun updateDrawerContent(portfolioNames : List<String>) {
        val navigationView : NavigationView? = activity?.findViewById<NavigationView>(R.id.nav_view)

        val menu : Menu? = navigationView?.menu

        val TAG = "PortfoliosFragment"
        Log.d(TAG, "updateDrawerContent: portfolioNames.size = " + portfolioNames.size)
        val loops = if(portfolioNames.size > 7) 7 else portfolioNames.size

        for(i in 1..loops){
            val menuItemId = "nav_item".plus(i)

            Log.d(TAG, "updateDrawerContent: portfolioName" + i + " = " + portfolioNames[i-1])
            val id : Int = resources.getIdentifier(menuItemId, "id", activity?.packageName!!)

            val navItem : MenuItem? = menu?.findItem(id)

            if(i<= portfolioNames.size) {
                navItem?.apply {
                    title = portfolioNames[i - 1]
                }
            }
        }
    }

    interface PortfolioItemListener{
        fun onPortfolioClick(portFolioName : String)
    }

    companion object {
        fun newInstance() : PortfoliosFragment {
            return PortfoliosFragment()
        }
    }
}