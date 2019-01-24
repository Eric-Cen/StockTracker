package com.mcarving.stocktracker

interface BaseView<T> {
    fun setPresenter(presenter : T)
}