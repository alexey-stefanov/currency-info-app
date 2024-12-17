package com.alexstefanov.currencyinfoapp.data.repository.currency

import com.alexstefanov.currencyinfoapp.app.utils.Result
import com.alexstefanov.currencyinfoapp.app.utils.safeApiCall
import com.alexstefanov.currencyinfoapp.data.remote.CurrencyApiService
import com.alexstefanov.currencyinfoapp.data.remote.response.toDomain
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import com.alexstefanov.currencyinfoapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {

    override suspend fun getCurrencySymbols(): Result<Map<String, String>> {
        val result = safeApiCall {
            apiService.getCurrencySymbols().symbols
        }

        return when (result) {
            is Result.Success -> {
                Result.Success(result.data)
            }

            is Result.Failure -> {
                Result.Failure(result.error)
            }
        }
    }

    override suspend fun getLatestCurrencies(
        selectedCurrency: String,
        symbols: List<String>?
    ): Result<List<CurrencyModel>> {
        val result = safeApiCall {
            apiService.getLatestCurrencies(
                base = selectedCurrency,
                symbols = symbols?.toString()
            )
        }

        return when (result) {
            is Result.Success -> {
                Result.Success(result.data.toDomain())
            }

            is Result.Failure -> {
                Result.Failure(result.error)
            }
        }
    }
}