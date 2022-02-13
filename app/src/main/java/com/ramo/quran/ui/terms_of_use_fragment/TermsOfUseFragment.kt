package com.ramo.quran.ui.terms_of_use_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ramo.core.ext.safeContext
import com.ramo.quran.R
import com.ramo.quran.core.BaseFragment
import com.ramo.quran.databinding.FragmentTermsOfUseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsOfUseFragment : BaseFragment<FragmentTermsOfUseBinding, TermsOfUseViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSend.setOnClickListener { onSendClick() }
    }

    private fun onSendClick() {
        val description = binding.textInputErrorMessage.text ?: ""
        if (description.isBlank()) {
            safeContext {
                Toast.makeText(it, getString(R.string.feedback_warning), Toast.LENGTH_SHORT).show()
            }
        } else {
            viewModel.sendFeedBack(description.toString())
            toast(getString(R.string.thanks_feedback))
            binding.textInputErrorMessage.text = null
        }
    }
}