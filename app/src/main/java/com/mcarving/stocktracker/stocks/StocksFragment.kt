package com.mcarving.stocktracker.stocks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.addStock.AddStockActivity
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.portfolios.PortfoliosActivity
import com.mcarving.stocktracker.stockDetail.StockDetailActivity
import com.mcarving.stocktracker.util.Utils

class StocksFragment : StocksContract.View, Fragment() {

    private lateinit var mPresenter: StocksContract.Presenter
    private lateinit var mStocksAdapter: StockAdapter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewManager: RecyclerView.LayoutManager
    private var mPortfolioName: String = ""

    private var mDrawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null

    private var mItemListener = object : StockItemListener {
        override fun onStockClick(symbol: String) {
            mPresenter.openStockDetails(symbol)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStocksAdapter = StockAdapter(listOf<Stock>(), mItemListener)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.result(requestCode, resultCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_portfolios, container, false)

        mViewManager = LinearLayoutManager(context)

        val fab: FloatingActionButton? = activity?.findViewById<FloatingActionButton>(R.id.fab_add)

        fab?.apply {
            setOnClickListener {
                Utils.showToastMessage(context, "to start add stock")

                mPresenter.addNewStock()
            }
        }

        mRecyclerView = root.findViewById<RecyclerView>(R.id.rv_portfolio).apply {
            setHasFixedSize(true)
        }

        mRecyclerView.layoutManager = mViewManager
        mRecyclerView.adapter = mStocksAdapter

        arguments?.getString(PORTFOLIO_NAME_EXTRA)?.let {
            mPortfolioName = it
        }
        setTitle(mPortfolioName)

        return root
    }

    override fun showStocks(stocks: List<Stock>) {
        mStocksAdapter.replaceData(stocks)
    }

    override fun showAddStock() {

        val intent = Intent(context, AddStockActivity::class.java)
//            .apply{
//                putExtra(PORTFOLIO_NAME_EXTRA, mPortfolioName)
//            }
        val TAG = "StocksFragment"

        Log.d(TAG, "showAddStock: portfolioname = " + mPortfolioName)
        intent.putExtra(PORTFOLIO_NAME_EXTRA, mPortfolioName)
        startActivityForResult(intent, AddStockActivity.REQUEST_ADD_STOCK)
    }

    override fun showStockDetailUI(symbol: String) {

        val intent = Intent(context, StockDetailActivity::class.java)
        intent.putExtra(StockDetailActivity.EXTRA_STOCK_SYMBOL, symbol)
        startActivity(intent)
    }

    override fun showPortfolioUI(portfolio: String) {
        val intent = Intent(context, StocksActivity::class.java)
        intent.putExtra(StocksActivity.EXTRA_PORTFOLIO_NAME, portfolio)
        startActivity(intent)

    }

    override fun showPortfolioList() {
        val intent = Intent(context, PortfoliosActivity::class.java)
        startActivity(intent)
    }

    override fun showLoadingStockError() {
        Utils.showToastMessage(context, "failed to load stock list")
    }

    override fun showNoStocks() {
        Utils.showToastMessage(context, "There is no stock to show")
    }

    override fun setTitle(title: String) {
        val newTiltle = "Portfolio: ".plus(title)
        (activity as AppCompatActivity).supportActionBar?.title = newTiltle
    }

    override fun setPresenter(presenter: StocksContract.Presenter) {
        mPresenter = presenter
    }

    override fun setupDrawerContent() {

        mDrawerLayout = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)

        navigationView = activity?.findViewById<NavigationView>(R.id.nav_view_stocks)
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
                        mPresenter.openPortfolioList()
                    }
                    else -> { // Note the block
                        Utils.showToastMessage(context, "other is selected")
                    }
                }

                true
            }
        }
    }


    private fun loadDetails(menutItem: MenuItem) {
        Utils.showToastMessage(context, menutItem.toString())
        val name = menutItem.toString()
        if (name.isNotEmpty()) {
            mPresenter.openPortfolio(name)
        }
    }

    override fun updateDrawerContent(portfolioNames : List<String>) {
        val navigationView: NavigationView? = activity?.findViewById<NavigationView>(R.id.nav_view_stocks)

        val menu: Menu? = navigationView?.menu

        val TAG = "StocksActivity"
        Log.d(TAG, "updateDrawerContent: portfolioNames.zie = " + portfolioNames.size)

        val loops = if (portfolioNames.size > 7) 7 else portfolioNames.size

        for (i in 1..loops) {
            val menuItemId = "nav_item".plus(i)

            val id: Int = resources.getIdentifier(menuItemId, "id", activity?.packageName!!)

            val navItem: MenuItem? = menu?.findItem(id)
            Log.d(TAG, "updateDrawerContent: portfolioName" + i + " = " + portfolioNames[i - 1])
            if (i <= portfolioNames.size) {
                navItem?.apply {
                    title = portfolioNames[i - 1]
                    Log.d(TAG, "updateDrawerContent: in if portfolioName" + i + " = " + title)
                }
            }
        }
    }


    interface StockItemListener {
        fun onStockClick(symbol: String)
    }

    companion object {
        const val PORTFOLIO_NAME_EXTRA = "portfolio_name"

        fun newInstance(portfolioName: String) = StocksFragment().apply {
            arguments = Bundle().apply {
                putString(PORTFOLIO_NAME_EXTRA, portfolioName)
            }
        }

//        fun newInstance(portfolioName : String) : StocksFragment {
//
//            val fragment = StocksFragment()
//            val bundle = Bundle()
//            bundle.putString(PORTFOLIO_NAME_EXTRA, portfolioName)
//            fragment.arguments = bundle
//
//            return fragment
//        }
    }
}