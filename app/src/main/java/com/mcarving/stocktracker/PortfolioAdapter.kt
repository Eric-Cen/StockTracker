package com.mcarving.stocktracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class PortfolioAdapter(private val portfolioList : Array<String>) :
        RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_portfolio, parent, false) as TextView

        return PortfolioViewHolder(textView)

    }

    override fun getItemCount(): Int {

        return portfolioList.size
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        holder.textView.text = portfolioList[position]
    }


    class PortfolioViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}