package com.mcarving.stocktracker.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.data.Purchase
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.DateConverter
import com.mcarving.stocktracker.data.source.ListConverter

/**
 * The Room database that contains The stocks table
 */

@Database(entities = arrayOf(Stock::class, Purchase::class, Portfolio::class), version = 1)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class StocksDatabase : RoomDatabase() {
    abstract fun stockDao() : StocksDao
    abstract fun purchaseDao() : PurchaseDao
    abstract fun portfolioDao() : PortfolioDao

    companion object {

        @Volatile
        private var INSTANCE : StocksDatabase? = null

        fun getDatabase(context: Context) : StocksDatabase =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    StocksDatabase::class.java,
                    "Stocks.db")
                    .build()
    }

}