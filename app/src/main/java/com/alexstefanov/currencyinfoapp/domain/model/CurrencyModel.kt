package com.alexstefanov.currencyinfoapp.domain.model

data class CurrencyModel(
    val baseCurrencyCode: String,
    val targetCurrencyCode: String,
    val rate: Double
)
