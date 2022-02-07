package com.ramo.quran.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

fun EditText.textChangeDelayedListener(delayTime: Long, textChange: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        private var timer = Timer()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            timer.cancel()
        }

        override fun afterTextChanged(text: Editable?) {
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    val query = text?.toString()
                    textChange.invoke(query ?: "")
                }
            }, delayTime)
        }
    })
}