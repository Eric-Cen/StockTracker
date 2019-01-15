package com.mcarving.stocktracker.data.source.remote

import android.content.Context
import android.support.annotation.NonNull
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.api.PortfolioResponse
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.data.source.local.PortfolioSharedPreferences
import com.mcarving.stocktracker.util.AppExecutors
import org.xmlpull.v1.XmlPullParser.TYPES
import retrofit2.Call
import retrofit2.Response

class StocksRemoteDataSource private constructor(
    private val mAppExecutors: AppExecutors,
    private val mApiService: ApiService
) : StocksDataSource {

    override fun getStocksByPortfolio(context: Context,
                                      portfolioName: String,
                                      callback: StocksDataSource.LoadStocksCallback) {
        // get symbol string list from SharedPreferences
        val stockSymbols = PortfolioSharedPreferences(context)
            .getSymbolsFromPortfolio(portfolioName)


        // convert stockList<String> to a string for retrofit request
        val symbolsString: String = getStringFromSymbolList(stockSymbols)

        if (symbolsString == "") {
            callback.onDataNotAvailable()
        } else {
            val call: Call<Map<String, PortfolioResponse>> = mApiService.queryStockList(symbolsString, TYPES)

            call.enqueue(object : retrofit2.Callback<Map<String, PortfolioResponse>> {

                override fun onResponse(
                    call: Call<Map<String, PortfolioResponse>>,
                    response: Response<Map<String, PortfolioResponse>>
                ) {
                    if (response.isSuccessful) {
                        val resultMap = response.body()
                        resultMap?.let {

                            val stocks: MutableList<Stock> = mutableListOf()
                            it.forEach { _, portfolioResponse ->

                                val stock: Stock = portfolioResponse.quote.toStock()
                                stocks.add(stock)
                            }
                            callback.onStocksLoaded(stocks)
                        }
                    } else {
                        callback.onDataNotAvailable()
                    }
                }

                override fun onFailure(call: Call<Map<String, PortfolioResponse>>, t: Throwable) {
                    callback.onDataNotAvailable()
                }
            })
        }
    }


    private fun getStringFromSymbolList(@NonNull stockSymbols: List<String>?): String {

        var stockParam = ""

        stockSymbols?.let {
            for ((index, value) in stockSymbols.withIndex()) {
                stockParam += if (index == stockSymbols.lastIndex) value else value.plus(",")

            }
        }

        // expected string = "sq,aapl,msft"
        return stockParam
    }

    override fun getStock(context: Context,
                          symbol: String, callback:
                          StocksDataSource.GetStockCallback) {
        val call: Call<Map<String, PortfolioResponse>> = mApiService.queryStockList(symbol, TYPES)

        call.enqueue(object : retrofit2.Callback<Map<String, PortfolioResponse>> {

            override fun onResponse(
                call: Call<Map<String, PortfolioResponse>>,
                response: Response<Map<String, PortfolioResponse>>
            ) {
                if (response.isSuccessful) {
                    val resultMap = response.body()
                    resultMap?.let {
                        val stock: Stock = it.getValue(symbol).quote.toStock()
                        callback.onStockLoaded(stock)
                    }
                } else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<Map<String, PortfolioResponse>>, t: Throwable) {
                callback.onDataNotAvailable()
            }
        })
    }


    override fun refreshStock(stock: Stock) {
        //not implemented
    }

    override fun refreshStocks(updatedStocks: List<Stock>) {
        //not implemented
    }

    override fun saveStock(context: Context, stock: Stock, portfolioName: String) {
        //not implemented
    }

    override fun deletePortfolio(context: Context, portfolioName: String) {
        //not implemented
    }

    override fun deleteStock(context: Context, symbol: String, portfolioName : String) {
        //not implemented
    }

    companion object {
        @Volatile
        private var INSTANCE: StocksRemoteDataSource? = null

        fun getInstance(
            @NonNull appExecutors: AppExecutors,
            @NonNull apiService: ApiService
        ): StocksRemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StocksRemoteDataSource(appExecutors, apiService).also { INSTANCE = it }
            }

        const val BASE_API_URL = "https://api.iextrading.com/1.0/"
        const val TYPES = "quote"
    }
}