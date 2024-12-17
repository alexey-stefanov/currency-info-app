package com.alexstefanov.currencyinfoapp.data.remote.response

import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel

data class CurrenciesResponse(
    val base: String,
    val rates: Map<String, Double>
)

fun CurrenciesResponse.toDomain(): List<CurrencyModel> {
    return rates.map { (code, rate) ->
        CurrencyModel(
            baseCurrencyCode = base,
            targetCurrencyCode = code,
            rate = rate
        )
    }
}