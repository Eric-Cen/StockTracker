package com.mcarving.stocktracker.api

data class Quote (val symbol : String,
                  val companyName : String,
                  val latestPrice : Double,
                  val latestUpdate : Long,
                  val change : Double,
                  val changePercent : Double
                  )