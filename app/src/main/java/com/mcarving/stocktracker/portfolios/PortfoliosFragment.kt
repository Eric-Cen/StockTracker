package com.mcarving.stocktracker.portfolios

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.addPortfolio.AddPortfolioActivity
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.portfolioDetail.PortfolioDetailActivity
import com.mcarving.stocktracker.util.Utils
import okhttp3.internal.Util

class PortfoliosFragment : PortfoliosContract.View, Fragment() {

    private lateinit var mPresenter: PortfoliosContract.Presenter
    private lateinit var mPortfoiloAdapter: PortfolioAdapter

    private var mItemListener = object : PortfolioItemListener{
        override fun onPortfolioClick(portFolioName: String) {
            mPresenter.openPortfolioDetails(portFolioName)
        }
    }


    override fun showPortfolios(portfolios: List<String>) {
        mPortfoiloAdapter.replaceData(portfolios)
    }

    override fun showAddPortfolio() {
       val intent = Intent(context, AddPortfolioActivity::class.java)
        startActivityForResult(intent, AddPortfolioActivity.REQUEST_ADD_PORTFOLIO)

    }

    override fun showPortfolioDetailUi(portfolioName: String) {

        val intent = Intent(context, PortfolioDetailActivity::class.java)
        intent.putExtra(PortfolioDetailActivity.EXTRA_PORTFOLIO_NAME, portfolioName)
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
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_portfolios, container, false)
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
