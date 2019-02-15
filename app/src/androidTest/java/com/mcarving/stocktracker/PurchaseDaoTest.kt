package com.mcarving.stocktracker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mcarving.stocktracker.data.Purchase
import com.mcarving.stocktracker.data.source.local.PurchaseDao
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class PurchaseDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var purchaseDao : PurchaseDao
    private lateinit var database : StocksDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getTargetContext()

        // Using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(context,
            StocksDatabase::class.java)
            //allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        purchaseDao = database.purchaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetPurchase(){
        val purchaseTest = Purchase(
            "AAPL",
            "Portfolio1",
            1.0,
            Date(),
            Date(),
            2.0,
            null,
            null,
            null)

        purchaseDao.insert(purchaseTest)

        val allPurchases = purchaseDao.getPurchases()
        assertEquals(allPurchases[0].symbol, purchaseTest.symbol)
    }

    @Test
    @Throws(Exception::class)
    fun getAllStocks(){
        val purchase1 = Purchase(
            "AAPL",
            "Portfolio1",
            1.0,
            Date(),
            Date(),
            2.0,
            null,
            null,
            null)
        purchaseDao.insert(purchase1)

        val purchase2 = Purchase(
            "SQ",
            "Portfolio2",
            1.0,
            Date(),
            Date(),
            2.0,
            null,
            null,
            null)
        purchaseDao.insert(purchase2)

        val allPurchases = purchaseDao.getPurchases()
        assertEquals(allPurchases[0].symbol, purchase1.symbol)
        assertEquals(allPurchases[1].portfolioName, purchase2.portfolioName)
    }


    @Test
    @Throws(Exception::class)
    fun deleteAll(){
        val purchase1 = Purchase(
            "AAPL",
            "Portfolio1",
            1.0,
            Date(),
            Date(),
            2.0,
            null,
            null,
            null)
        purchaseDao.insert(purchase1)

        val purchase2 = Purchase(
            "SQ",
            "Portfolio2",
            1.0,
            Date(),
            Date(),
            2.0,
            null,
            null,
            null)
        purchaseDao.insert(purchase2)

        purchaseDao.deleteAll()

        val allPurchases = purchaseDao.getPurchases()
        assertTrue(allPurchases.isEmpty())
    }

}