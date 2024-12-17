package com.alexstefanov.currencyinfoapp.domain.repository

import com.alexstefanov.currencyinfoapp.app.utils.Result
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): Result<Map<String, String>>
    suspend fun getLatestCurrencies(
        selectedCurrency: String,
        symbols: List<String>? = null
    ): Result<List<CurrencyModel>>
}