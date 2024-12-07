package com.alexstefanov.currencyinfoapp.data.remote

import com.alexstefanov.currencyinfoapp.data.remote.response.CurrenciesResponse
import com.alexstefanov.currencyinfoapp.data.remote.response.CurrencySymbolsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("symbols")
    suspend fun getCurrencySymbols(): CurrencySymbolsResponse

    @GET("latest")
    suspend fun getLatestCurrencies(
        @Query("base") base: String,
        @Query("symbols") symbols: String? = null
    ): CurrenciesResponse
}