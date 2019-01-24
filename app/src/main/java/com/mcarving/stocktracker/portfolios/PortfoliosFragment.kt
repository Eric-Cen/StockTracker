package com.mcarving.stocktracker.portfolios

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.data.Portfolio

class PortfoliosFragment : Fragment(), PortfoliosContract.View {

    private lateinit var mPresenter: PortfoliosContract.Presenter
    private lateinit var mPortfoiloAdapter: PortfolioAdapter

    private var mItemListener = object : PortfolioItemListener{
        override fun onPortfolioClick(portFolioName: String) {
            mPresenter.openPortfolioDetails()
        }
    }


    override fun showPortfolios(portfolios: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAddPortfolio() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPortfolioDetailUi(portfolioId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingPortfolioError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoPortfolios() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun setPresenter(presenter: PortfoliosContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPortfoiloAdapter = PortfolioAdapter(listOf<String>(), mItemListener)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_portfolios, container, false)
    }


    companion object {
        fun newInstance() : PortfoliosFragment {
            return PortfoliosFragment()
        }
    }

    interface PortfolioItemListener{
        fun onPortfolioClick(portFolioName : String)
    }
}
