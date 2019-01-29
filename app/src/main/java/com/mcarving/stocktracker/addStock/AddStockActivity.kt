package com.mcarving.stocktracker.addStock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import com.mcarving.stocktracker.R

class AddStockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setTitle("Add Stock")
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        if(null == savedInstanceState){
            initFragment(AddStockFragment())
        }


    }

    private fun initFragment(addStockFragment : Fragment){
        // Add the AddStockFragment to the layout
        val transaction = supportFragmentManager.beginTransaction()

        transaction.add(R.id.contentFrame, addStockFragment)
        transaction.commit()
    }
}
