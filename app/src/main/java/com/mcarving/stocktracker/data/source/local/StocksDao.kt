package com.mcarving.stocktracker.data.source.local

import android.arch.persistence.room.*
import com.mcarving.stocktracker.data.Stock

/**
 * Data Access Object for the Stocks table.
 */
@Dao
interface StocksDao {

    /**
     * Select all stocks from the stocks table
     */
    @Query("SELECT * FROM " + Stock.TABLE_NAME)
    fun getStocks() : List<Stock>


    /**
     * Select a stock by symbol
     */
    @Query("SELECT * FROM " + Stock.TABLE_NAME + " where symbol = :stockSymbol")
    fun getStockBySymbol(stockSymbol: String) : Stock

    /**
     * Insert a stock in the database. If the stock already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stock : Stock)

    /**
     * Update a stock
     *
     * @param stock stock to be updated
     * @return the number of stocks updated. This should always be 1.
     */
    @Update
    fun update(stock : Stock) : Int


    /**
     * Delete a stock by symbol
     *
     * @return the number of stocks deleted. This should always be 1.
     */
    @Query("DELETE FROM " + Stock.TABLE_NAME + " WHERE symbol = :stockSymbol")
    fun deleteStockBySymbol(stockSymbol : String) : Int


    @Query("DELETE FROM " + Stock.TABLE_NAME)
    fun deleteAll()
}