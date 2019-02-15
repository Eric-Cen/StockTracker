package com.mcarving.stocktracker.addPortfolio

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.mcarving.stocktracker.R
import com.mcarving.stocktracker.data.Stock
import com.mcarving.stocktracker.util.Utils

class AddPortfolioFragment : Fragment(), AddPortfolioContract.View {
    private lateinit var mName: EditText

    private lateinit var mPresenter: AddPortfolioContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fab: FloatingActionButton? = activity?.findViewById<FloatingActionButton>(R.id.fab_edit_portfolio_done)

        fab?.setImageResource(R.drawable.ic_done)
        fab?.setOnClickListener {
            mPresenter.savePortfolio(mName.text.toString())
            activity?.apply{
                finish()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_portfolio, container, false)
        mName = root.findViewById<EditText>(R.id.et_add_portfolio_name)
        return root
    }


    override fun showPortfoliosList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    override fun showSaveError(message: String) {
        Utils.showToastMessage(context, message)
    }


    override fun setPresenter(presenter: AddPortfolioContract.Presenter) {
        mPresenter = presenter
    }


    companion object {
        fun newInstance(): AddPortfolioFragment {
            return AddPortfolioFragment()
        }
    }
}