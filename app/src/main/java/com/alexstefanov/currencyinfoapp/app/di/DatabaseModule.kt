package com.alexstefanov.currencyinfoapp.app.di

import android.content.Context
import androidx.room.Room
import com.alexstefanov.currencyinfoapp.data.local.AppDatabase
import com.alexstefanov.currencyinfoapp.data.local.dao.FavoritePairDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "currency_info_app_db"
        ).build()
    }
    
    @Provides
    fun provideFavoritePairDao(appDatabase: AppDatabase): FavoritePairDao {
        return appDatabase.favoritePairDao()
    }
}