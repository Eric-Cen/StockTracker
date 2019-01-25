package com.mcarving.stocktracker.data.source.local

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson

class PortfolioSharedPreferences constructor(
    val context: Context,
    private val gson: Gson = Gson()
) {

    fun addPortfolioName(portfolioName: String) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)

        val jsonText = preferences.getString(PORTFOlIO_NAMES, null)

        if (jsonText == null) {
            // if null, create a new portfolio with the given name
            val newPortfolios = listOf(portfolioName)
            val newJsonText = gson.toJson(newPortfolios)
            preferences.edit().putString(PORTFOlIO_NAMES, newJsonText).apply()

        } else {
            // if not null, try to add to the current list
            val currentPortfolios: List<String> = gson.fromJson(jsonText, Array<String>::class.java).asList()

            //check if portfolioName is not already in the list
            if (currentPortfolios.all { it != portfolioName }) {
                val newList = mutableListOf<String>()
                currentPortfolios.forEach {
                    newList.add(it)
                }

                newList.add(portfolioName)

                val newJsonText2 = gson.toJson(newList)

                preferences.edit().putString(PORTFOlIO_NAMES, newJsonText2).apply()

            }
        }
    }

    fun removePortfolioName(portfolioName: String) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)

        // check if portfolioName already exists
        // if not, create a new portfolio with the given name
        val jsonText = preferences.getString(portfolioName, null)

        // if not null, try to add to the current list
        val currentPortfolios: List<String> = gson.fromJson(jsonText, Array<String>::class.java).asList()

        //check if symbol is not already in the list
        if (currentPortfolios.any { it == portfolioName }) {
            // it is in the list, remove it
            val newList = mutableListOf<String>()
            currentPortfolios.forEach {

                // exclude the portfolioName when copying to the new list
                if (it != portfolioName) {
                    newList.add(it)
                }
            }

            val newJsonText2 = gson.toJson(newList)
            preferences.edit().putString(PORTFOlIO_NAMES, newJsonText2).apply()

        }

    }

    fun getPortfolioNames(): List<String> {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)

        val jsonText = preferences.getString(PORTFOlIO_NAMES, null)

        val newList = mutableListOf<String>()

        if (jsonText != null) {
            // if not null, try to add to the current list
            val currentPortfolios: List<String> = gson.fromJson(jsonText, Array<String>::class.java).asList()

            currentPortfolios.forEach {
                newList.add(it)
            }
        }

        return newList
    }

    fun addSymbolToPortfolio(symbol: String, portfolioName: String) {

        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)

        // check if portfolioName already exists
        // if not, create a new portfolio with the given name
        val jsonText = preferences.getString(portfolioName, null)

        if (jsonText == null) {
            // if null, create a new portfolio with the given name
            val newSymbolList = listOf(symbol)
            val newJsonText = gson.toJson(newSymbolList)
            preferences.edit().putString(portfolioName, newJsonText).apply()


        } else {
            // if not null, try to add to the current list
            val currentStockSymbols: List<String> = gson.fromJson(jsonText, Array<String>::class.java).asList()

            //check if symbol is not already in the list
            if (currentStockSymbols.all { it != symbol }) {
                // add stock symbol to the portfolio
                val newList = mutableListOf<String>()
                currentStockSymbols.forEach {
                    newList.add(it)
                }

                newList.add(symbol)

                val newJsonText2 = gson.toJson(newList)

                preferences.edit().putString(portfolioName, newJsonText2).apply()
            }
        }
    }

    fun getSymbolsFromPortfolio(portfolioName: String): List<String>? {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)

        // check if portfolioName already exists
        // if not, create a new portfolio with the given name
        val jsonText = preferences.getString(portfolioName, null) ?: return null

        return gson.fromJson(jsonText, Array<String>::class.java).asList()
    }

    fun removeSymbolFromPortfolio(symbol: String, portfolioName: String) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)

        // check if portfolioName already exists
        // if not, create a new portfolio with the given name
        val jsonText = preferences.getString(portfolioName, null)

        // if not null, try to add to the current list
        val currentStockSymbols: List<String> = gson.fromJson(jsonText, Array<String>::class.java).asList()

        //check if symbol is not already in the list
        if (currentStockSymbols.any { it == symbol }) {
            // it is in the list, remove it
            val newList = mutableListOf<String>()
            currentStockSymbols.forEach {

                // exclude the symbol when copying to the new list
                if (it != symbol) {
                    newList.add(it)
                }
            }

            val newJsonText2 = gson.toJson(newList)
            preferences.edit().putString(portfolioName, newJsonText2).apply()

        }
    }

    fun removePortfolio(portfolioName: String) {
        val prefEditor = PreferenceManager
            .getDefaultSharedPreferences(context).edit()

        prefEditor.remove(portfolioName)
        prefEditor.apply()
    }

    companion object {
        private const val PORTFOlIO_NAMES = "portfolios"
    }
}