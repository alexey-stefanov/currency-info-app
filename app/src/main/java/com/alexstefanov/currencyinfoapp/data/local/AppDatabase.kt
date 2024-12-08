package com.alexstefanov.currencyinfoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexstefanov.currencyinfoapp.data.local.dao.FavoritePairDao
import com.alexstefanov.currencyinfoapp.data.local.entity.FavoritePairEntity

@Database(entities = [FavoritePairEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritePairDao(): FavoritePairDao
}