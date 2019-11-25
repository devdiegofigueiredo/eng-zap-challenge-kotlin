package br.com.zaptest.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat

class Formatters {

    companion object {
        fun moneyMask(editText: EditText) = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                editText.removeTextChangedListener(this)

                val cleanString = charSequence.toString().replace(Regex("[R$,.]"), "").trim()

                val formatted = NumberFormat.getCurrencyInstance().format((cleanString.toDouble() / 100))

                editText.setText(formatted)
                editText.setSelection(formatted.length)

                editText.addTextChangedListener(this)
            }
        }
    }
}