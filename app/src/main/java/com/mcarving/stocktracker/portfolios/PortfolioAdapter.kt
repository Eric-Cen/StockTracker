package com.mcarving.stocktracker.portfolios

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcarving.stocktracker.R

class PortfolioAdapter(private val portfolioList : List<String>,
                       private val itemListener: PortfoliosFragment.PortfolioItemListener) :
        RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_portfolio, parent, false) as View

        return PortfolioViewHolder(view)

    }

    override fun getItemCount(): Int {

        return portfolioList.size
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val TAG = "PortfolioAdapter"
        Log.d(TAG, "onBindViewHolder: item @ $position = ${portfolioList[position]}")
        holder.textView?.text = portfolioList[position]
    }


    class PortfolioViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        // holds the TextView that will add each porfolio to
        val textView = view.findViewById<TextView>(R.id.tv_portfolio_item)
    }

}