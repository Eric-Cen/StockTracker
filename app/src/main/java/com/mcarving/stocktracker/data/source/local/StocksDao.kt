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
    @Query("SELECT * FROM Stocks")
    fun getStocks() : List<Stock>

    /**
     * Select all stocks by a portfolio from stocks table
     */
    @Query("SELECT * FROM Stocks where byPortfolio = :portfolioName")
    fun getStocksByPortfolio(portfolioName : String) : List<Stock>
    //TODO portfolioName = "%portfolio name%"

    /**
     * Select a stock by symbol
     */
    @Query("SELECT * FROM Stocks where symbol = :stockSymbol")
    fun getStockBySymbol(stockSymbol: String) : Stock

    /**
     * Insert a stock in the database. If the stock already exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStock(stock : Stock)

    /**
     * Update a stock
     *
     * @param stock stock to be updated
     * @return the number of stocks updated. This should always be 1.
     */
    @Update
    fun updateStock(stock : Stock) : Int


    /**
     * Delete a stock by symbol
     *
     * @return the number of stocks deleted. This should always be 1.
     */
    @Query("DELETE FROM Stocks WHERE symbol = :stockSymbol")
    fun deleteStockBySymbol(stockSymbol : String) : Int

    /**
     * Delete stocks by a specific portfolio
     */
//    @Query("DELETE FROM Stocks WHERE byPortfolio LIKE :portfolioName")
//    fun deleteStocksByPortfolio(portfolioName: String) : Int
    //TODO search = "%portfolio name%"

}