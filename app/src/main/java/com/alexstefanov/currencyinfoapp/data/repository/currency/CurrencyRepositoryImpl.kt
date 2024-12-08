package com.alexstefanov.currencyinfoapp.data.repository.currency

import com.alexstefanov.currencyinfoapp.data.remote.CurrencyApiService
import com.alexstefanov.currencyinfoapp.domain.mapper.toDomain
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {

    override suspend fun getCurrencySymbols(): Map<String, String> {
        return apiService.getCurrencySymbols().symbols
    }

    override suspend fun getLatestCurrencies(
        selectedCurrency: String,
        symbols: List<String>?
    ): List<CurrencyModel> {
        val response =
            apiService.getLatestCurrencies(base = selectedCurrency, symbols = symbols?.toString())
        return response.toDomain()
    }
}