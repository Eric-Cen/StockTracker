package com.mcarving.stocktracker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mcarving.stocktracker.data.source.local.StocksDao
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*
import com.mcarving.stocktracker.data.Stock
import junit.framework.Assert.assertTrue


@RunWith(AndroidJUnit4::class)
class StocksDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var stocksDao : StocksDao
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

        stocksDao = database.stockDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetStock(){
        val stockTest = Stock(
            "aapl",
            "Apple Inc.",
            200.0,
            170.0,
            1.0,
            Date()
        )

        stocksDao.insert(stockTest)

        val allStocks = stocksDao.getStocks()
        assertEquals(allStocks[0].symbol, stockTest.symbol)
    }

    @Test
    @Throws(Exception::class)
    fun getAllStocks(){
        val stock1 = Stock(
            "aapl",
            "Apple Inc.",
            200.0,
            170.0,
            1.0,
            Date()
        )
        stocksDao.insert(stock1)

        val stock2 = Stock(
            "sq",
            "Square Inc.",
            200.0,
            170.0,
            1.0,
            Date()
        )

        stocksDao.insert(stock2)

        val allStocks = stocksDao.getStocks()
        assertEquals(allStocks[0].companyName, stock1.companyName)
        assertEquals(allStocks[1].symbol, stock2.symbol)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll(){

        val stock1 = Stock(
            "aapl",
            "Apple Inc.",
            200.0,
            170.0,
            1.0,
            Date()
        )

        stocksDao.insert(stock1)

        val stock2 = Stock(
            "sq",
            "Square Inc.",
            200.0,
            170.0,
            1.0,
            Date()
        )

        stocksDao.insert(stock1)

        stocksDao.deleteAll()
        val allStocks = stocksDao.getStocks()
        assertTrue(allStocks.isEmpty())

    }
}