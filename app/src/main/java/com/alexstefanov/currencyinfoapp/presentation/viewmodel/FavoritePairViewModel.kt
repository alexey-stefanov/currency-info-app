package com.alexstefanov.currencyinfoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstefanov.currencyinfoapp.data.repository.favoritecurrency.FavoritePairRepository
import com.alexstefanov.currencyinfoapp.domain.mapper.toFavoritePairUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePairViewModel @Inject constructor(
    private val repository: FavoritePairRepository
) : ViewModel() {

    val favoritePairs = repository.getAllFavoritePairs().map {
        it.map { favoritePair ->
            favoritePair.toFavoritePairUi()
        }
    }

    fun removePairFromFavorites(pairCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removePairFromFavorites(pairCode)
        }
    }
}