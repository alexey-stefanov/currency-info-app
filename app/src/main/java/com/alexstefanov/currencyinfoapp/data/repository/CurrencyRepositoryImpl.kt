package com.alexstefanov.currencyinfoapp.data.repository

import com.alexstefanov.currencyinfoapp.data.remote.CurrencyApiService
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {

    override suspend fun getCurrencySymbols(): Map<String, String> {
        return apiService.getCurrencySymbols().symbols
    }

    override suspend fun getLatestCurrencies(selectedCurrency: String): Map<String, Double> {
        return apiService.getLatestCurrencies(base = selectedCurrency).rates
    }
}