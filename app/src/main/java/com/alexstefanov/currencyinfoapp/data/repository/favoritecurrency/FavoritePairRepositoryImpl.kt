package com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency

import com.alexstefanov.currencyinfoapp.data.local.dao.FavoritePairDao
import com.alexstefanov.currencyinfoapp.data.local.entity.toDomain
import com.alexstefanov.currencyinfoapp.data.local.entity.toEntity
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel
import com.alexstefanov.currencyinfoapp.domain.repository.FavoritePairRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritePairRepositoryImpl @Inject constructor(
    private val dao: FavoritePairDao
) : FavoritePairRepository {

    override fun getAllFavoritePairs(): Flow<List<CurrencyModel>> {
        val entitiesFlow = dao.getAll()
        return entitiesFlow.map { it.map { entity -> entity.toDomain() } }
    }

    override fun getPairsForBaseCurrency(baseCurrencyCode: String): List<String> {
        return dao.getPairsForBaseCurrency(baseCurrencyCode)
    }

    override suspend fun addPairToFavorites(currencyModel: CurrencyModel) {
        val entity = currencyModel.toEntity()
        dao.insert(entity)
    }

    override suspend fun removePairFromFavorites(pairCode: String) {
        dao.delete(pairCode)
    }
}