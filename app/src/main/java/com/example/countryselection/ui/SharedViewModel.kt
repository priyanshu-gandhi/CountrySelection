package com.example.countryselection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countryselection.data.CountryRepository
import com.example.countryselection.domain.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _selectedCountry = MutableStateFlow<Country?>(null)
    val selectedCountry = _selectedCountry.asStateFlow()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()
    private var originalList: List<Country> = emptyList()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {

            val list = countryRepository.getCountries()

            originalList = list
            _countries.value = list

            startAutoRemove()
        }
    }

    private fun startAutoRemove() {
        viewModelScope.launch {

            while (true) {

                delay(10000)

                val current = _countries.value.toMutableList()

                if (current.isNotEmpty()) {
                    current.removeAt(0)
                    _countries.value = current
                } else {
                    _countries.value = originalList
                }
            }
        }
    }

    fun refreshCountries() {
        _countries.value = originalList
    }

    fun selectCountry(country: Country?) {
        _selectedCountry.value = country
    }
}