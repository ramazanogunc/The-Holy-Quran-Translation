package com.ramo.quran.ui.fragment

import androidx.fragment.app.Fragment
import com.ramo.quran.data.AppDatabase

open class HasDatabaseFragment : Fragment() {
    protected val appDatabase by lazy { AppDatabase.getDatabase(requireContext()) }
}