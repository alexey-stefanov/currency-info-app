package com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency

import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow

interface FavoritePairRepository {
    fun getAllFavoritePairs(): Flow<List<CurrencyModel>>
    suspend fun getPairsForBaseCurrency(baseCurrencyCode: String): List<String>
    fun getPairsForBaseCurrencyFlow(baseCurrencyCode: String): Flow<List<String>>
    suspend fun addPairToFavorites(currencyModel: CurrencyModel)
    suspend fun removePairFromFavorites(pair: String)
}