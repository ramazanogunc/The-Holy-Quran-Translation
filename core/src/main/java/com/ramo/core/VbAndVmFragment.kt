package com.ramo.core

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ramo.core.ext.findGenericWithType

abstract class VbAndVmFragment<VB : ViewBinding, VM : ViewModel> : ViewBindingFragment<VB>() {

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