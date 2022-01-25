package com.ramo.quran.core

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ramo.quran.core.ext.findGenericWithType

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : SimpleBaseFragment<VB>() {

    protected lateinit var viewModel: VM

    open val isSharedViewModel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val vmClass = javaClass.findGenericWithType<VM>(1)
        viewModel = ViewModelProvider(
            if (isSharedViewModel) requireActivity()
            else this
        )[vmClass]
    }

}