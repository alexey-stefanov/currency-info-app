package com.alexstefanov.currencyinfoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstefanov.currencyinfoapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _selectedCurrency = MutableStateFlow<String>("USD")
    val selectedCurrency: StateFlow<String> = _selectedCurrency

    private val _currencySymbols = MutableStateFlow<Map<String, String>>(emptyMap())
    val currencySymbols: StateFlow<Map<String, String>> = _currencySymbols

    private val _currencies = MutableStateFlow<Map<String, Double>>(emptyMap())
    val currencies: StateFlow<Map<String, Double>> = _currencies

    init {
        getLatestCurrency()
        getCurrencySymbols()
    }

    fun selectCurrency(currency: String) {
        _selectedCurrency.value = currency
        getLatestCurrency()
    }

    fun getCurrencySymbols() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencySymbols = currencyRepository.getCurrencySymbols()
            _currencySymbols.value = currencySymbols
        }
    }

    fun getLatestCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            val latestCurrencies = currencyRepository.getLatestCurrencies(selectedCurrency.value)
            _currencies.value = latestCurrencies
        }
    }
}