package com.alexstefanov.currencyinfoapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pairs")
data class FavoritePairEntity(
    @PrimaryKey
    val pair: String,
    val baseCurrencyCode: String,
    val targetCurrencyCode: String,
    val rate: Double
)
