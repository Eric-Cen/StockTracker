package com.mcarving.stocktracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var portfolioAdapter: PortfolioAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val strList = arrayOf("AAPL", "YHOO", "AWS", "SQ")
        viewManager = LinearLayoutManager(this)
        portfolioAdapter = PortfolioAdapter(strList) // TODO initialize

        recyclerView = findViewById<RecyclerView>(R.id.rv_portfolio).apply {
            setHasFixedSize(true)


        }





    }

}
