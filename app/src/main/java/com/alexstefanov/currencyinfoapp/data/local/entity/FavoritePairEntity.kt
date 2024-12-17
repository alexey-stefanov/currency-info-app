package com.alexstefanov.currencyinfoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexstefanov.currencyinfoapp.domain.model.CurrencyModel

@Entity(tableName = "favorite_pairs")
data class FavoritePairEntity(
    @PrimaryKey
    val pair: String,
    val baseCurrencyCode: String,
    val targetCurrencyCode: String,
    val rate: Double
)

fun FavoritePairEntity.toDomain(): CurrencyModel {
    return CurrencyModel(
        baseCurrencyCode = pair.split("/").first(),
        targetCurrencyCode = pair.split("/").last(),
        rate = rate
    )
}

fun CurrencyModel.toEntity(): FavoritePairEntity {
    return FavoritePairEntity(
        pair = "$baseCurrencyCode/$targetCurrencyCode",
        baseCurrencyCode = baseCurrencyCode,
        targetCurrencyCode = targetCurrencyCode,
        rate = rate
    )
}