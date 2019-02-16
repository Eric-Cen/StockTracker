package com.mcarving.stocktracker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.google.gson.Gson
import com.mcarving.stocktracker.data.Portfolio
import com.mcarving.stocktracker.data.source.local.PortfolioDao
import com.mcarving.stocktracker.data.source.local.StocksDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PortfolioDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var portfolioDao: PortfolioDao
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

        portfolioDao = database.portfolioDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPortfolio(){
        val portfolioTest = Portfolio("portfolio1", mutableListOf("aa", "bb", "cc"))

        portfolioDao.insert(portfolioTest)

        val allPortfolio = portfolioDao.getPortfolios()
        assertEquals(allPortfolio[0].name, portfolioTest.name)

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPortfolioNames(){
        val portfolioTest = Portfolio("portfolio1", mutableListOf("a2", "b2", "c2"))
        val portfolioTest2 = Portfolio("portfolio2", mutableListOf("d2", "e2", "f2"))
        val portfolioTest3 = Portfolio("portfolio3", mutableListOf("g2", "h2", "i2"))

        portfolioDao.insert(portfolioTest)
        portfolioDao.insert(portfolioTest2)
        portfolioDao.insert(portfolioTest3)

        val allPortfolio = portfolioDao.getPortfolioNames()
        assertEquals(allPortfolio[0], portfolioTest.name)
        assertEquals(allPortfolio[1], portfolioTest2.name)
        assertEquals(allPortfolio[2], portfolioTest3.name)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetSymbolsByPortfolioName(){
        val portfolioTest = Portfolio("portfolio1", mutableListOf("z1", "z2", "z3"))

        portfolioDao.insert(portfolioTest)

        val p = portfolioDao.getPortfolioByName(portfolioTest.name)
        //val symbols = Gson().fromJson(symbolsString, Array<String>::class.java).asList()
        val symbols = p.symbolList
        assertEquals(symbols[0], portfolioTest.symbolList[0])
        assertEquals(symbols[1], portfolioTest.symbolList[1])
        assertEquals(symbols[2], portfolioTest.symbolList[2])
    }


    @Test
    @Throws(Exception::class)
    fun deleteAll(){
        val portfolioTest = Portfolio("portfolio1", mutableListOf("z1", "z3", "z3"))

        portfolioDao.insert(portfolioTest)

        val portfolioTest2 = Portfolio("portfolio22", mutableListOf("z4", "z2", "z5"))

        portfolioDao.insert(portfolioTest2)

        val portfolioTest3 = Portfolio("portfolio22", mutableListOf("z4", "z5", "z6"))

        portfolioDao.insert(portfolioTest3)


        portfolioDao.deleteAll()
        val allPortfolios = portfolioDao.getPortfolios()

        assertTrue(allPortfolios.isEmpty())

    }


}