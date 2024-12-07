package com.alexstefanov.currencyinfoapp.data.remote.response

data class CurrenciesResponse(
    val base: String,
    val rates: Map<String, Double>
)
