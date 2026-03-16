package com.example.countryselection.data

import com.example.countryselection.domain.Country
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    suspend fun getCountries() : List<Country> {
        return apiInterface.getCountries()
    }
}