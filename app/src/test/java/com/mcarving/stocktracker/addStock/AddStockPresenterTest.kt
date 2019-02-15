package com.mcarving.stocktracker.addStock

import android.content.Context
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.data.source.StocksDataSource
import com.mcarving.stocktracker.data.source.StocksRepository
import org.hamcrest.CoreMatchers.any
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor

import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Unit tests for the implementation of {@Link AddStockPresenter}.
 */
class AddStockPresenterTest {
    @Mock
    private lateinit var mStocksRepository : StocksRepository

    @Mock
    private lateinit var mAddStockView : AddStockContract.View

    @Captor
    private lateinit var mGetStockCallbackCaptor : ArgumentCaptor<StocksDataSource.GetStockCallback>

    private lateinit var mAddStockPresenter: AddStockPresenter
    private val context = mock(Context::class.java)
    private val mPortfolioName = "PortfolioTest"

    @Before
    fun setupMocksAndView(){
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun createPresenter_setsThePresenterToView(){
        // Get a reference to the class under test
        mAddStockPresenter = AddStockPresenter(context,
            mAddStockView,
            mPortfolioName,
            mStocksRepository)

        //Then the presenter is set to the view
        verify(mAddStockView).setPresenter(mAddStockPresenter)
    }

    @Test
    fun saveNewStockToRepository_showsSuccessMessageUi(){
        // Get a reference to the class under test
        mAddStockPresenter = AddStockPresenter(context,
            mAddStockView,
            mPortfolioName,
            mStocksRepository)


        // When the presenter is asked to save a stock
        val stockTest = Stock("aapl",
            "Apple Inc.",
            200.0,
            170.0,
            1.0,
            Date())
        mAddStockPresenter.stockToAdd = stockTest

        mAddStockPresenter.saveStock()


        // Then a stock is saved in the repository and the view updated
        verify(mStocksRepository).saveStock(context, stockTest, mPortfolioName)
        verify(mAddStockView).showStocksList(true)
    }

    @Test
    fun saveStock_emptyStockShowsErrorUi(){
        // Get a reference to the class under test
        mAddStockPresenter = AddStockPresenter(context,
            mAddStockView,
            mPortfolioName,
            mStocksRepository)

        // When the presenter is asked to save a stock which is null
        mAddStockPresenter.saveStock()

        // Then an empty error msg is shown in the UI
        verify(mAddStockView).showSaveError()
    }

    @Test
    fun loadStock_ShowsSuccessMessageUi(){
        // Get a reference to the class under test
        mAddStockPresenter = AddStockPresenter(context,
            mAddStockView,
            mPortfolioName,
            mStocksRepository)

        val stockSymbol = "aapl"

        // When the presenter is asked to load a stock from internet
        mAddStockPresenter.loadStock(stockSymbol)


        // Then the stock repository is required
//        mGetStockCallbackCaptor.apply {
//            verify(mStocksRepository).getStock(context,
//                stockSymbol,
//                capture())
//        }



        // Simulate callback
        val stockTest = Stock("aapl",
            "Apple Inc.",
            200.0,
            170.0,
            1.0,
            Date())
//        mGetStockCallbackCaptor.value.onStockLoaded(stockTest)
//        mGetStockCallbackCaptor.apply {
//            value.onStockLoaded(stockTest)
//        }
//
//        // Then a stock is loaded and view is updated
//       verify(mAddStockView).showStock(stockTest)
    }


}