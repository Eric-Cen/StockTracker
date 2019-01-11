package com.mcarving.stocktracker.data.source.remote

import com.mcarving.stocktracker.data.source.StocksDataSource

class StocksRemoteDataSource private constructor(): StocksDataSource {
    override fun getStocks(callback: StocksDataSource.LoadStocksCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStock(symbol: String, callback: StocksDataSource.GetStockCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshStocks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllStocks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteStock(symbol: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}