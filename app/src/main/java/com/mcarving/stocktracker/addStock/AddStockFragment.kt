package com.mcarving.stocktracker.addStock

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.util.Utils

class AddStockFragment : Fragment(), AddStockContract.View {
    private lateinit var mPresenter : AddStockContract.Presenter

    private lateinit var mSymbolSearch: EditText
    private lateinit var mButtonSearch : Button
    private lateinit var mButtonAdd : Button
    private lateinit var mTextViewName : TextView
    private lateinit var mTextViewSymbol : TextView
    private lateinit var mTextViewPrice : TextView

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_add_stock, container, false)

        mSymbolSearch = root.findViewById<EditText>(R.id.et_add_stock_input)
        mButtonSearch = root.findViewById<Button>(R.id.bt_add_stock_search)
        mButtonSearch.setOnClickListener {

            val searchSymbol = mSymbolSearch.text.toString()

            mPresenter.loadStock(searchSymbol)
            // retrieve stock data from internet
        }
        mButtonAdd = root.findViewById<Button>(R.id.bt_add_stock_add)
        mButtonAdd.setOnClickListener {
            // save stock to ROOM database
            mPresenter.saveStock()
        }

        mTextViewName = root.findViewById<TextView>(R.id.tv_add_stock_name)
        mTextViewSymbol = root.findViewById<TextView>(R.id.tv_add_stock_symbol)
        mTextViewPrice = root.findViewById<TextView>(R.id.tv_add_stock_price)

        return root
    }

    override fun showInputError() {

    }

    override fun showStockError() {
        val root = activity?.findViewById<View>(R.id.layout_add_stock_detail)
        val toast = Toast.makeText(context,"No stock information found for the symbol", Toast.LENGTH_LONG )

        root?.let{
            val yOffSet = Math.max(0, it.height - toast.yOffset)
            toast.setGravity(Gravity.TOP, 0, yOffSet)
            toast.show()
        }

        mTextViewName.text = getString(R.string.company)
        mTextViewSymbol.text = getString(R.string.symbol)
        mTextViewPrice.text = getString(R.string.price)

        mButtonAdd.isClickable = false
        mButtonAdd.alpha = 0.5f

    }

    override fun showSaveError() {
        val root = activity?.findViewById<View>(R.id.layout_add_stock_detail)
        val toast = Toast.makeText(context,"No stock information to save", Toast.LENGTH_LONG )

        root?.let{
            val yOffSet = Math.max(0, it.height - toast.yOffset)
            toast.setGravity(Gravity.TOP, 0, yOffSet)
            toast.show()
        }
    }

    override fun showStock(stock: Stock) {
        val company = "Company: " + stock.companyName
        mTextViewName.text = company


        val symbol = "Symbol: ".plus(stock.symbol)
        mTextViewSymbol.text = symbol

        val price = "Price: " + stock.currentPrice.toString()
        mTextViewPrice.text = price

        mButtonAdd.isClickable = true
        mButtonAdd.alpha = 1.0f
    }

    override fun showStocksList(isStockAdded : Boolean) {
        val resultCode = if(isStockAdded) Activity.RESULT_OK
                              else Activity.RESULT_CANCELED

        activity?.apply{
            setResult(resultCode)
            finish()
        }
    }


    override fun setPresenter(presenter: AddStockContract.Presenter) {
        mPresenter = presenter
    }

    companion object {
        fun newInstance() = AddStockFragment()
    }
}