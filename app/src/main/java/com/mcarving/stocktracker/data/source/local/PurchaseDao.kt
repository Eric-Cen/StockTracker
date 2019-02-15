package com.mcarving.stocktracker.data.source.local

import android.arch.persistence.room.*
import com.mcarving.stocktracker.data.Purchase

@Dao
interface PurchaseDao{

    /**
     * select all purchases from the purchases table
     */
    @Query("SELECT * from purchases")
    fun getPurchases() : List<Purchase>


    /**
     * Select stock(s) by portfolio name
     */
    @Query("SELECT * FROM purchases where portfolioName = :portfolio ")
    fun getStocksByPortfolio(portfolio : String) : List<Purchase>

    /**
     * Insert a purchase into the database. If the purchase already exists, replace it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(purchase: Purchase)

    /**
     * Update a purchase
     * @return the number of purchases updated.  This should always be 1
     */
    @Update
    fun update(purchase: Purchase) : Int

    /**
     * delete a stock by symbol
     * @return the number of purchases deleted. This should always be 1.
     */
    @Query("DELETE FROM purchases where  id = :purchaseID")
    fun deletePurchaceById(purchaseID : Int) : Int

    /**
     * Delete a list of purchases by portfolio name
     */
    @Query("DELETE FROM purchases where portfolioName = :portfolio")
    fun deletePurchasesByPortfolio(portfolio: String) :  Int

    @Query("DELETE FROM PURCHASES")
    fun deleteAll()
}