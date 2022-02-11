package com.ramo.quran.core

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.ramo.core.VbAndVmActivity
import com.ramo.core.ext.observe
import com.ramo.quran.utils.CommonDialogs

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : VbAndVmActivity<VB, VM>() {

    private val dialog: Dialog by lazy { CommonDialogs.loadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        observe(viewModel.toast) { toast ->
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        }
    }
}