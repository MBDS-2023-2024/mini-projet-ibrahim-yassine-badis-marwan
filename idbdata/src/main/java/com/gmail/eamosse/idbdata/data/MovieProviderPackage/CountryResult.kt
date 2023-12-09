package com.gmail.eamosse.idbdata.data.MovieProviderPackage

import com.gmail.eamosse.idbdata.data.MovieProvider

data class CountryResult (
    val link: String,
    val buy: List<MovieProvider>,
    val rent: List<MovieProvider>,
    val flatrate: List<MovieProvider>
)