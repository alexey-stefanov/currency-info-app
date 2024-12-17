package com.alexstefanov.currencyinfoapp.app.di

import com.alexstefanov.currencyinfoapp.domain.repository.CurrencyRepository
import com.alexstefanov.currencyinfoapp.data.repository.currency.CurrencyRepositoryImpl
import com.alexstefanov.currencyinfoapp.domain.repository.FavoritePairRepository
import com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency.FavoritePairRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository

    @Binds
    @Singleton
    fun bindFavoritePairRepository(
        favoritePairRepositoryImpl: FavoritePairRepositoryImpl
    ): FavoritePairRepository
}