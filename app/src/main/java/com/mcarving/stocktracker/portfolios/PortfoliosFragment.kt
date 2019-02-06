package com.mcarving.stocktracker.portfolios

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcarving.stocktracker.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mcarving.stocktracker.addPortfolio.AddPortfolioActivity
import com.mcarving.stocktracker.data.Portfolio
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

    interface PortfolioItemListener{
        fun onPortfolioClick(portFolioName : String)
    }

    companion object {
        fun newInstance() : PortfoliosFragment {
            return PortfoliosFragment()
        }
    }
}