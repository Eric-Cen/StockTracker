package com.mcarving.stocktracker.stocks

class StocksFragment {


    interface StockItemListener {
        fun onStockClick(symbol : String)
    }
}