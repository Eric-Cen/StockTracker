package com.mcarving.stocktracker

interface BaseView<T> {
    fun start(presenter : T)
}