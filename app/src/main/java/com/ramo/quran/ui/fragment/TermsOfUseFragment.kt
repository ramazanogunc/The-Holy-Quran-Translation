package com.ramo.quran.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.ramo.quran.BuildConfig
import com.ramo.quran.R
import com.ramo.quran.ext.showError

class TermsOfUseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_terms_of_use, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<Button>(R.id.buttonSend).setOnClickListener { onSendClick() }
    }

    private fun onSendClick() {
        val input = requireView().findViewById<TextInputEditText>(R.id.textInputErrorMessage)
        if (input.text.isNullOrEmpty()) {
            requireActivity().showError()
        } else {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>("ramazanogunc@hotmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack - Kuran Meali")
            intent.putExtra(Intent.EXTRA_TEXT, input.text.toString() + getMetaInfo())

            startActivity(Intent.createChooser(intent, "Send"))
        }
    }

    private fun getMetaInfo(): String {
        return "\n\nVersion:" + BuildConfig.VERSION_NAME + "\nAndroid Version:" + Build.VERSION.BASE_OS
    }
}