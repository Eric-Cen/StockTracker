package com.mcarving.stocktracker.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = Portfolio.TABLE_NAME)
data class  Portfolio(
    @PrimaryKey
    var name : String,

    var symbolList : MutableList<String>){

    companion object {
        const val TABLE_NAME = "portfolios"
    }
}