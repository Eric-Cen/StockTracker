package com.mcarving.stocktracker.stocks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.addStock.AddStockActivity
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.portfolios.PortfoliosActivity
import com.mcarving.stocktracker.stockDetail.StockDetailActivity
import com.mcarving.stocktracker.util.Utils

class StocksFragment : StocksContract.View, Fragment(){

    private lateinit var mPresenter : StocksContract.Presenter
    private lateinit var mStocksAdapter : StockAdapter

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mViewManager : RecyclerView.LayoutManager
    private var mPortfolioName : String = ""

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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_portfolios, container, false)

        mViewManager = LinearLayoutManager(context)

        val fab : FloatingActionButton? = activity?.findViewById<FloatingActionButton>(R.id.fab_add)

        fab?.apply {
            setOnClickListener {
                Utils.showToastMessage(context, "to start add stock")

                val intent = Intent(context, AddStockActivity::class.java)
                startActivity(intent)
            }
        }

        mRecyclerView = root.findViewById<RecyclerView>(R.id.rv_portfolio).apply {
            setHasFixedSize(true)
        }

        mRecyclerView.layoutManager = mViewManager
        mRecyclerView.adapter = mStocksAdapter

        arguments?.getString(PORTFOLIO_NAME_EXTRA)?.let{
            mPortfolioName = it
        }
        setTitle(mPortfolioName)

        return root
    }

    override fun showStocks(stocks: List<Stock>) {
        mStocksAdapter.replaceData(stocks)
    }

    override fun showAddStock() {

        Log.d("what4.5", "showAddStock: ")
        val intent = Intent(context, AddStockActivity::class.java)
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

    override fun setTitle(title : String) {
        val newTiltle = "Portfolio: ".plus(title)
        (activity as AppCompatActivity).supportActionBar?.title = newTiltle
    }

    override fun setPresenter(presenter: StocksContract.Presenter) {
        mPresenter = presenter
    }

    interface StockItemListener {
        fun onStockClick(symbol : String)
    }

    companion object {
        const val PORTFOLIO_NAME_EXTRA = "portfolio_name"

//        fun newInstance(portfolioName: String) = StocksFragment().apply {
//            arguments = Bundle().apply {
//                putString(PORTFOLIO_NAME_EXTRA, portfolioName)
//            }
//        }

        fun newInstance(portfolioName : String) : StocksFragment {

            val fragment = StocksFragment()
            val bundle = Bundle()
            bundle.putString(PORTFOLIO_NAME_EXTRA, portfolioName)
            fragment.arguments = bundle

            return fragment
        }
    }
}