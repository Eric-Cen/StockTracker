package com.mcarving.stocktracker.stocks

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.data.Stock

class StockAdapter(private var stockList : List<Stock>,
                    private val itemListener: StocksFragment.StockItemListener) :
        RecyclerView.Adapter<StockAdapter.StockViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_stock, parent, false) as View

        return StockViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    fun replaceData(stocks : List<Stock>){
        stockList = stocks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {

        val stock = stockList[position]
        holder.textViewSymbol?.text = stock.symbol
        holder.textViewPrice?.text = stock.currentPrice.toString()
        holder.textViewDate?.text = stock.previousClosedPrice.toString()

    }

    inner class StockViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val textViewSymbol : TextView?= view.findViewById<TextView>(R.id.tv_stocks_symbol)
        val textViewPrice : TextView? = view.findViewById<TextView>(R.id.tv_stocks_price)
        val textViewDate : TextView?= view.findViewById<TextView>(R.id.tv_stocks_date)
    }

}