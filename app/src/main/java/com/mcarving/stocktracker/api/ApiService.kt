package com.mcarving.stocktracker.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.iextrading.com/1.0/stock/aapl/quote
// https://api.iextrading.com/1.0/stock/market/batch?symbols=fb,sq,tsla&types=quote

interface ApiService {
    @GET("stock/market/batch")
    abstract fun queryStockList(@Query("symbols") stocks: String,
                                @Query("types") types: String) : Call<PortfolioResponse>
}