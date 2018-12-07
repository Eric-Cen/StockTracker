package com.mcarving.stocktracker.data

import java.util.*

data class Stock(
    var currentPrice : Double,
    var previousClosedPrice : Double,
    var quantity : Double,
    var purchaseDate : Date)