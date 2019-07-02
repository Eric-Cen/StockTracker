package com.mcarving.stocktracker.data

import java.util.*

data class StockAlert(val symbol : String, val percentage : Double = 0.0, val price : Double) {

    var trailingDay : Int = 0
    var trailingSetDay : Date? = null

}

// by percentage of current price
// by percentage of a specific price

// percentage, 2, 3, 6 or 8%  => only deal with percentage for now
// 50day average
// 20day average
// 5day average
// MACD
// RSI

//TODO
// how to store alerts into database along with the stock information
//