package com.example.countryselection.domain

data class Country(
    val id : Int,
    val name: String,
    val callingCodes: List<String>,
    val flags: Flags
)

data class Flags(
    val png: String,
    val svg: String
)