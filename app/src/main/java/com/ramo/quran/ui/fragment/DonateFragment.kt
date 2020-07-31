package com.ramo.quran.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ramo.quran.R
import com.ramo.quran.helper.showError


class DonateFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_donate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonWatchAds).setOnClickListener { onWatchAdsClick() }
        view.findViewById<Button>(R.id.buttonLearnBank).setOnClickListener { onLearnBankClick() }

    }

    private fun onWatchAdsClick() {
        activity?.showError()
    }

    private fun onLearnBankClick() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>("ramazanogunc@hotmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bağış - Kuran Meali")
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.doante_mail))

        startActivity(Intent.createChooser(intent, "Send"))
    }

}