package com.example.countryselection.data

import com.example.countryselection.domain.Country
import retrofit2.http.GET

interface ApiInterface {

    @GET("countries")
    suspend fun getCountries() : List<Country>
}


