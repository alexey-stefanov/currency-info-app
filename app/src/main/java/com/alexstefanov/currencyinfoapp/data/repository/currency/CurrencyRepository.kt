package com.alexstefanov.currencyinfoapp.data.repository.currency

import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): Map<String, String>
    suspend fun getLatestCurrencies(selectedCurrency: String, symbols: List<String>? = null): List<CurrencyModel>
}