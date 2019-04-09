package com.mcarving.stocktracker.api

import android.os.Build.VERSION_CODES.N
import com.mcarving.stocktracker.data.Stock
import java.util.*

data class Quote (val symbol : String,
                  val companyName : String,
                  val latestPrice : Double,
                  val latestUpdate : Long,
                  val change : Double,
                  val changePercent : Double
                  ) {
    fun toStock() : Stock {
        return Stock(symbol,
            companyName,
            latestPrice,
            latestUpdate,
            change,
            changePercent)
    }
}