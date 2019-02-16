package com.mcarving.stocktracker.data.source

import android.arch.persistence.room.TypeConverter
import android.util.Log
import com.google.gson.Gson

class ListConverter {

    @TypeConverter
    fun jsonToList(jsonText: String) : MutableList<String>{

        val TAG = "ListConverter"
        val newList = mutableListOf<String>()
        val currentList = Gson().fromJson(jsonText, Array<String>::class.java).asList()
        Log.d(TAG, "2 jsonToList: jsontext = " + jsonText)
        Log.d(TAG, "2 jsonToList: convert to currentList: " + currentList.toString())
        currentList.forEach {
            newList.add(it)
        }

        Log.d(TAG, "2 jsonToList: newList: " + newList.toString())
        return newList
    }

    @TypeConverter
    fun listToJson(symbols : MutableList<String>) : String {
        val jText = Gson().toJson(symbols)
        val TAG = "ListConverter"
        Log.d(TAG, "1 listToJson: before convert: symbols = " + symbols.toString())
        Log.d(TAG, "1 listToJson: jText = " + jText)
        return jText
    }

}