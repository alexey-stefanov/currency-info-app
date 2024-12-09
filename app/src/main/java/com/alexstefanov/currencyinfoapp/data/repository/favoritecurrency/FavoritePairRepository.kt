package com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency

import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow

interface FavoritePairRepository {
    fun getAllFavoritePairs(): Flow<List<CurrencyModel>>
    fun getPairsForBaseCurrency(baseCurrencyCode: String): List<String>
    suspend fun addPairToFavorites(currencyModel: CurrencyModel)
    suspend fun removePairFromFavorites(pair: String)
}