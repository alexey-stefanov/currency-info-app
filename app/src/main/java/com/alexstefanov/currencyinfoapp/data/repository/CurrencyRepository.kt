package com.alexstefanov.currencyinfoapp.data.repository

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): Map<String, String>
    suspend fun getLatestCurrencies(selectedCurrency: String): Map<String, Double>
}