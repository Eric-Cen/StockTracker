package com.mcarving.stocktracker.addStock

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcarving.stocktracker.data.Stock

class AddStockFragment : Fragment(), AddStockContract.View {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun showEmptyStockError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showStock(stock: Stock) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showStocksList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPresenter(presenter: AddStockContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}