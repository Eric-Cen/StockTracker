package com.mcarving.stocktracker.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mcarving.stocktracker.data.Stock

/**
 * The Room database that contains The stocks table
 */

@Database(entities = arrayOf(Stock::class), version = 1)
abstract class StocksDatabase : RoomDatabase() {
    abstract fun stockDao()

    companion object {

        @Volatile private var INSTANCE : StocksDatabase? = null

        fun getInstance(context: Context) : StocksDatabase =
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