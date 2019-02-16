package com.mcarving.stocktracker.data.source.local

import android.arch.persistence.room.*
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.data.source.ListConverter

@Dao
interface PortfolioDao {

    /**
     * Select all portfolios from portfolios table
     */
    @Query("SELECT * FROM portfolios")
    fun getPortfolios() : List<Portfolio>

    /**
     * Select all portfolio names from portfolios table
     */
    @Query("SELECT name FROM portfolios")
    fun getPortfolioNames() : List<String>

    /**
     * Select symbols by portfolio name
     */
    @TypeConverters(ListConverter::class)
    @Query("SELECT * FROM portfolios WHERE name = :portfolioName")
    fun getPortfolioByName(portfolioName : String) : Portfolio



    /**
     * Insert a portfolio into the database.  If the portfolio already exists, replace it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(portfolio : Portfolio)

    /**
     * Update a portfolio
     * @return the number of portfolios updated. This should always be 1.
     */
    @Update
    fun update(portfolio: Portfolio) : Int


    /**
     * Delete a Portfolio entry by name
     * @return the number of porfolios deleted. This should always be 1.
     */
    @Query("DELETE FROM portfolios where name = :portfolioName")
    fun deletePortfolioByName(portfolioName : String) : Int


    /**
     * delete all content for the portfolios table
     */
    @Query("DELETE FROM portfolios")
    fun deleteAll()

}