package com.mcarving.stocktracker.data.source

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date?{
        return value?.let{Date(it)}
    }

    @TypeConverter
    fun dateToTimestamp(date : Date?): Long?{
        return date?.time?.toLong()
    }

}