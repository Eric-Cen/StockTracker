package com.mcarving.stocktracker.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkHelper {

    fun isNetworkAvailable(context : Context) : Boolean {
        val connectivityManager=
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork : NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        return isConnected
    }

    companion object {
        @Volatile
        private var networkHelper : NetworkHelper? = null

        fun getInstance() : NetworkHelper =
            networkHelper ?: synchronized(this) {
                networkHelper ?: NetworkHelper().also { networkHelper = it }
            }

    }
}