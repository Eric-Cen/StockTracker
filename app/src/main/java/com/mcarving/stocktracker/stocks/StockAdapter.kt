package com.mcarving.stocktracker.stocks

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.data.Stock
import java.text.DecimalFormat

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
        holder.textViewCompany?.text = stock.companyName
        holder.textViewPrice?.text = stock.latestPrice.toString()
        holder.textViewDate?.text = stock.latestUpdate.toString()
        holder.textViewChange?.text = stock.change.toString()
        val formatter = DecimalFormat("#0.00")
        val percentage : String = formatter.format(stock.changePercent * 100).toString() + "%"
        holder.textViewChangePercent?.text = percentage

        //TODO how to show the purchase data and trailing data??

    }

    inner class StockViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val textViewSymbol : TextView?= view.findViewById<TextView>(R.id.tv_stocks_symbol)
        val textViewCompany : TextView? = view.findViewById<TextView>(R.id.tv_stocks_company)
        val textViewPrice : TextView? = view.findViewById<TextView>(R.id.tv_stocks_price)
        val textViewDate : TextView?= view.findViewById<TextView>(R.id.tv_stocks_time)
        val textViewChange : TextView?= view.findViewById<TextView>(R.id.tv_stocks_change_price)
        val textViewChangePercent : TextView?= view.findViewById<TextView>(R.id.tv_stocks_change_percentage)

    }

}