package com.mcarving.stocktracker.Settings

import android.os.Bundle
import android.preference.PreferenceFragment
import com.mcarving.stocktracker.R

class SettingsFragment : PreferenceFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}