package com.mcarving.stocktracker.data.source.remote

import android.support.annotation.NonNull
import com.mcarving.stocktracker.api.ApiService
import com.mcarving.stocktracker.api.PortfolioResponse
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.util.AppExecutors
import org.xmlpull.v1.XmlPullParser.TYPES
import retrofit2.Call
import retrofit2.Response

class StocksRemoteDataSource private constructor(
    private val mAppExecutors : AppExecutors,
    private val mApiService: ApiService
    ): StocksDataSource {

    override fun getStocksByPortfolio(portfolio: String, callback: StocksDataSource.LoadStocksCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStock(symbol: String, callback: StocksDataSource.GetStockCallback) {


        val call : Call<Map<String, PortfolioResponse>> = mApiService.queryStockList("sql", TYPES)

        call.enqueue(object : retrofit2.Callback<Map<String, PortfolioResponse>>{

            override fun onResponse(
                call: Call<Map<String, PortfolioResponse>>,
                response: Response<Map<String, PortfolioResponse>>
            ) {
                if(response.isSuccessful){
                    val resultMap = response.body()
                    resultMap?.let {
                        val stock : Stock = it.getValue(symbol).quote.toStock()
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

    override fun saveStock(stock: Stock) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshStock(stock: Stock) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshStockList(stocks: List<Stock>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteStocksByPortfolio(portfolio: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteStock(symbol: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        @Volatile private var INSTANCE : StocksRemoteDataSource? = null

        fun getInstance(@NonNull appExecutors : AppExecutors,
                        @NonNull apiService: ApiService) : StocksRemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: StocksRemoteDataSource(appExecutors, apiService).also { INSTANCE = it }
        }

        const val BASE_API_URL = "https://api.iextrading.com/1.0/"
        const val TYPES = "quote"
    }
}