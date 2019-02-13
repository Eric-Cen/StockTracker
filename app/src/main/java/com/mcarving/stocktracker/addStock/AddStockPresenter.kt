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
            mStocksRepository.saveStock(mContext, it, mPortfolioName)
            Utils.showToastMessage(mContext, "saving stock to database")
            mAddStockView.showStocksList(true)
        }
    }

    override fun loadStock(symbol: String) {
        //load data from internet or local storage

        val TAG = "AddStockPresenter"
        // load data fro internet
        //getStockInfo(symbol.toUpperCase())
        mStocksRepository.getStock(mContext, symbol.toUpperCase(),
            object : StocksDataSource.GetStockCallback{

                override fun onStockLoaded(stock: Stock) {
                    stockToAdd = stock

                    stockToAdd?.let {
                        Log.d(TAG, "onResponse: stock price = " + it.currentPrice)
                        mAddStockView.showStock(it)
                    }
                }

                override fun onDataNotAvailable() {
                    Log.d(TAG, "onFailure: Failed Call:")
                    mAddStockView.showEmptyStockError()
                }
            })

    }

    override fun start() {

        // Not implemented
    }

    private fun getStockInfo(symbol : String) {
        val TAG = "AddStockPresenter"
        Log.d(TAG, "getStockInfo: ${StocksRemoteDataSource.BASE_API_URL}")

        val retrofitRquest  = Retrofit.Builder()
            .baseUrl(StocksRemoteDataSource.BASE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val call : Call<Map<String, PortfolioResponse>> = retrofitRquest.queryStockList(symbol, "quote")

        call.enqueue(object : retrofit2.Callback<Map<String, PortfolioResponse>> {
            override fun onResponse(call: Call<Map<String, PortfolioResponse>>,
                                    response: retrofit2.Response<Map<String, PortfolioResponse>>) {
                Log.d(TAG, "onResponse: Successful Market Batch Query. Response.body=${response.body()}")

                Log.d(TAG, "onResponse: " + response.body()?.size)

                if(response.body()?.size == 0){
                    Log.d(TAG, "onResponse: else")
                    stockToAdd = null
                    mAddStockView.showEmptyStockError()
                } else {
                    val resultMap = response.body()

                    Log.d(TAG, "onResponse: if")
                    if(resultMap!=null) {
                        for ((key, value) in resultMap) {
                            Log.d(TAG, "onResponse: key = $key" + " price = ${value.quote.latestPrice}")
                        }

                    }
                    stockToAdd = resultMap?.get(symbol)?.quote?.toStock()

                    stockToAdd?.let {
                        Log.d(TAG, "onResponse: stock price = " + it.currentPrice)
                        mAddStockView.showStock(it)
                    }
                }

            }
            override fun onFailure(call: Call<Map<String, PortfolioResponse>>, t: Throwable) {
                Log.d(TAG, "onFailure: Failed Call: " + t)
                mAddStockView.showEmptyStockError()
            }
        })
    }
}

