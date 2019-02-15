package com.mcarving.stocktracker.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.util.*

@Entity(tableName = "purchases")
data class Purchase(

    var symbol : String,

    var portfolioName : String,

    var quantity : Double,

    var purchaseDate : Date,

    var sellDate : Date?,

    var purchasePrice : Double,

    var sellPrice : Double?,

    var TrailingDate : Date?,

    var TrailingPercent : Double?

    ) {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}