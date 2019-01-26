package com.mcarving.stocktracker.data.source

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date?{
        return value?.let{Date(it)}
    }

    @TypeConverter
    fun dateToTimestamp(date : Date?): Long?{
        return date?.time?.toLong()
    }
}