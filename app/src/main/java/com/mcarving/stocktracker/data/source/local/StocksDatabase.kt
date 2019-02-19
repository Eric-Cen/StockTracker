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
import java.util.concurrent.Executors

/**
 * The Room database that contains The stocks table
 */

@Database(entities = arrayOf(Stock::class, Purchase::class, Portfolio::class), version = 1)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class StocksDatabase : RoomDatabase() {
    abstract fun stockDao() : StocksDao
    abstract fun purchaseDao() : PurchaseDao
    abstract fun portfolioDao() : PortfolioDao


    /**
     * Inserts the dummy data into the database if it is current empty.
     */
    private fun populateInitialData(){
        Executors.newSingleThreadScheduledExecutor().execute {
            if(portfolioDao().count() == 0) {
                beginTransaction()
                try{
                    for (i in 1..7) {
                        val name = "Portfolio".plus(i)
                        val portfolio = Portfolio(name, mutableListOf(""))
                        portfolioDao().insert(portfolio)
                    }
                    setTransactionSuccessful()
                } finally {
                    endTransaction()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE : StocksDatabase? = null

        fun getDatabase(context: Context) : StocksDatabase =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: buildDatabase(context).also {
                        it.populateInitialData()
                        INSTANCE = it
                    }
                }

        private fun buildDatabase(context: Context) : StocksDatabase {
            val sInstance = Room.databaseBuilder(
                context.applicationContext,
                StocksDatabase::class.java,
                "Stocks.db")
                .build()

            return sInstance
        }
    }
}