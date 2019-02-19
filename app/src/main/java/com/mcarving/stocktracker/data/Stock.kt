package com.mcarving.stocktracker.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.util.*

/**
 * Immutable model class for a Stock
 */
@Entity(tableName = Stock.TABLE_NAME )
data class Stock(
    @PrimaryKey
    @NonNull
    var symbol : String,

    var companyName : String,
    var currentPrice : Double,
    var previousClosedPrice : Double,
    var quantity : Double,
    var purchaseDate : Date){

    companion object {
        const val TABLE_NAME = "stocks"
    }
}