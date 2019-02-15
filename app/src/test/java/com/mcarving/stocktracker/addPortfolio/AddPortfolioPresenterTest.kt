package com.mcarving.stocktracker.addPortfolio

import android.content.Context
import com.mcarving.stocktracker.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AddPortfolioPresenterTest {
    @Mock
    private lateinit var mAddPortfolioView : AddPortfolioContract.View

    private val context = mock(Context::class.java)

    private lateinit var mAddPortfolioPresenter: AddPortfolioPresenter

    @Before
    fun setupMocksAndView(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun createPresenter_setsThePresenterToView(){
        // Get a reference to the class under test
        mAddPortfolioPresenter = AddPortfolioPresenter(context,
            mAddPortfolioView)

        //Then the presenter is set to the view
        verify(mAddPortfolioView).setPresenter(mAddPortfolioPresenter)
    }

    @Test
    fun saveNewPortfolio_showsSuccessMessageUi(){
        // Get a reference to the class under test
        mAddPortfolioPresenter = AddPortfolioPresenter(context,
            mAddPortfolioView)

//        // When the presenter is asked to save a portfolio
//        mAddPortfolioPresenter.savePortfolio("testPortfolio")
//
//        // Then a portfolio is saved in sharedPreference and the view updated
//        verify(mAddPortfolioView).showPortfoliosList()

    }

    @Test
    fun savePortfolio_emptyInputShowsErrorUi(){

        // Get a reference to the class under test
        mAddPortfolioPresenter = AddPortfolioPresenter(context,
            mAddPortfolioView)

//        // When the presenter is asked to save an empty portfolio
//        mAddPortfolioPresenter.savePortfolio("")
//
//
//        // Then an empty error msg is shown in the UI
//        verify(mAddPortfolioView).showSaveError(context.getString(R.string.empty_portfolio_message))
    }
}