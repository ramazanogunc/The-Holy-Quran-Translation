package com.ramo.quran.core

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.ramo.core.VbAndVmFragment
import com.ramo.core.ext.observe
import com.ramo.core.ext.safeContext
import com.ramo.quran.utils.CommonDialogs

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : VbAndVmFragment<VB, VM>() {

    private val dialog: Dialog by lazy { CommonDialogs.loadingDialog(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        observe(viewModel.toast) { toastMessage ->
            safeContext {
                Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}