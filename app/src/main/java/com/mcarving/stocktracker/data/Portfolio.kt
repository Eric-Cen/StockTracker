package com.mcarving.stocktracker.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "portfolios")
data class  Portfolio(
    @PrimaryKey
    var name : String,

    var symbolList : MutableList<String>)