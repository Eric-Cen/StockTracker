package com.mcarving.stocktracker.addStock

import android.content.Context
import android.util.Log
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.api.PortfolioResponse
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.data.source.StocksRepository
import com.mcarving.stocktracker.data.source.remote.StocksRemoteDataSource
import com.mcarving.stocktracker.util.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AddStockPresenter constructor(
    private val mContext : Context,
    private val mAddStockView : AddStockContract.View,
    private val mPortfolioName : String,
    private val mStocksRepository: StocksRepository
) : AddStockContract.Presenter {

    var stockToAdd : Stock? = null

    init {
        mAddStockView.setPresenter(this)
    }

    override fun saveStock() {

        if(stockToAdd == null){
            mAddStockView.showSaveError()
        }

        stockToAdd?.let {
            //save to room database
            mStocksRepository.saveStock(it, mPortfolioName)
            mAddStockView.showStocksList(true)
        }
    }

    override fun loadStock(symbol: String) {
        // check String to see if it is a valid input, only letters

        // load data from internet if there is connection
        // else load data from local storage
        mStocksRepository.getStock(mContext, symbol.toUpperCase(),
            object : StocksDataSource.GetStockCallback{
                override fun onStockLoaded(stock: Stock) {
                    stockToAdd = stock

                    stockToAdd?.let {
                        mAddStockView.showStock(it)
                    }
                }

                override fun onDataNotAvailable() {
                    mAddStockView.showStockError()
                }
            })
    }

    override fun start() {
        // Not implemented
    }

}

