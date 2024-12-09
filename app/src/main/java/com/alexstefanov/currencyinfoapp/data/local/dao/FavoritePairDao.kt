package com.alexstefanov.currencyinfoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexstefanov.currencyinfoapp.data.local.entity.FavoritePairEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePairDao {

    @Query("SELECT * FROM favorite_pairs")
    fun getAll(): Flow<List<FavoritePairEntity>>

    @Query("SELECT targetCurrencyCode FROM favorite_pairs WHERE baseCurrencyCode = :baseCurrencyCode")
    fun getPairsForBaseCurrency(baseCurrencyCode: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoritePairEntity: FavoritePairEntity)

    @Query("DELETE FROM favorite_pairs WHERE pair = :pair")
    suspend fun delete(pair: String)
}