package com.alexstefanov.currencyinfoapp.domain.model

import androidx.annotation.StringRes
import com.alexstefanov.currencyinfoapp.R

enum class SortOrder(@StringRes val filterLabelRes: Int) {
    CodeAZ(R.string.sort_code_a_z),
    CodeZA(R.string.sort_code_z_a),
    QuoteAsc(R.string.sort_quote_asc),
    QuoteDesc(R.string.sort_quote_desc)
}