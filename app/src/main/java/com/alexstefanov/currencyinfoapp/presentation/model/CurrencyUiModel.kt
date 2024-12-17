package com.alexstefanov.currencyinfoapp.presentation.model

import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel

data class CurrencyUiModel(
    val codeLabel: String,
    val rateValue: String,
    val isFavorite: Boolean
)

fun CurrencyModel.toCurrencyUi(): CurrencyUiModel {
    return CurrencyUiModel(
        codeLabel = targetCurrencyCode,
        rateValue = rate.toString(),
        isFavorite = false
    )
}

fun CurrencyModel.toFavoritePairUi(): CurrencyUiModel {
    return CurrencyUiModel(
        codeLabel = "$baseCurrencyCode/$targetCurrencyCode",
        rateValue = rate.toString(),
        isFavorite = true
    )
}

// only for currency
fun CurrencyUiModel.toDomain(baseCurrencyCode: String): CurrencyModel {
    return CurrencyModel(
        baseCurrencyCode = baseCurrencyCode,
        targetCurrencyCode = codeLabel,
        rate = rateValue.toDouble()
    )
}