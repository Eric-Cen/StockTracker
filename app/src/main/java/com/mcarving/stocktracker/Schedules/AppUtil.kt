package com.mcarving.stocktracker.Schedules

import java.util.*

class AppUtil{

    companion object {
        private  var util : Utility = Utilities()

        fun load(newUtil : Utility){
            util = newUtil
        }

        fun getCurrentTime() : Calendar {
            return util.getCurrentTime()
        }

        fun getStarttime() : Calendar {
            return util.getStartTime()
        }

        fun getEndTime() : Calendar {
            return util.getEndTime()
        }

        fun getMidNight() : Calendar {
            return util.getMidNight()
        }
    }
}