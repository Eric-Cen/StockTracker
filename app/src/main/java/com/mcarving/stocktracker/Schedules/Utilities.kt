package com.mcarving.stocktracker.Schedules

import java.util.*

class Utilities : Utility{
    override fun getCurrentTime(): Calendar {
        return getTime()
    }

    override fun getStartTime(): Calendar {
        return getTime()

    }

    override fun getEndTime(): Calendar {
        return getTime()
    }


    override fun getMidNight(): Calendar {
        return getTime()
    }

    private fun getTime() : Calendar {
//        var mockedTime = Calendar.getInstance()
//        mockedTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
//        mockedTime.set(2019, Calendar.MARCH, 10)  // 3/10/2019, 3:00PM
//        mockedTime.set(Calendar.HOUR_OF_DAY, 15)
//        mockedTime.set(Calendar.MINUTE, 0)
//        mockedTime.set(Calendar.SECOND, 0)
//        mockedTime.set(Calendar.MILLISECOND, 0)

//        var mockedTime = Calendar.getInstance()
//        mockedTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
//        mockedTime.set(2019, Calendar.MARCH, 5)  // 3/5/2019, 10PM
//        mockedTime.set(Calendar.HOUR_OF_DAY, 22)
//        mockedTime.set(Calendar.MINUTE, 0)
//        mockedTime.set(Calendar.SECOND, 0)
//        mockedTime.set(Calendar.MILLISECOND, 0)

//        var mockedTime = Calendar.getInstance()
//        mockedTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
//        mockedTime.set(2019, Calendar.MARCH, 8)  // 3/8/2019, 7:30AM
//        mockedTime.set(Calendar.HOUR_OF_DAY, 7)
//        mockedTime.set(Calendar.MINUTE, 30)
//        mockedTime.set(Calendar.SECOND, 0)
//        mockedTime.set(Calendar.MILLISECOND, 0)

//        var mockedTime = Calendar.getInstance()
//        mockedTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
//        mockedTime.set(2019, Calendar.MARCH, 15)  // 3/8/2019, 5:30PM
//        mockedTime.set(Calendar.HOUR_OF_DAY, 17)
//        mockedTime.set(Calendar.MINUTE, 30)
//        mockedTime.set(Calendar.SECOND, 0)
//        mockedTime.set(Calendar.MILLISECOND, 0)
//
//        return mockedTime

        return Calendar.getInstance(TimeZone.getTimeZone("EST5EDT"))
    }
}

interface Utility{
    fun getCurrentTime() : Calendar
    fun getStartTime() : Calendar
    fun getEndTime() : Calendar
    fun getMidNight() : Calendar
}