package com.gmail.eamosse.idbdata.api.response

import com.google.gson.annotations.SerializedName

data class WatchProvidersResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results:  CountryResult
) {
    data class CountryResult(
        @SerializedName("link")
        val link: String,
        @SerializedName("buy")
        val buy: List<MovieProvider>,
        @SerializedName("rent")
        val rent: List<MovieProvider>,
        @SerializedName("flatrate")
        val flatrate: List<MovieProvider>
    )
    {
        internal fun toCountryResult() = CountryResult(
            link = link,
            buy = buy?.map { it.toMovieProvider() } ?: emptyList(),
            rent = rent?.map { it.toMovieProvider() } ?: emptyList(),
            flatrate = flatrate?.map { it.toMovieProvider() } ?: emptyList()
        )
    }
    data class MovieProvider(
        @SerializedName("logo_path")
        val logoPath: String,
        @SerializedName("provider_id")
        val providerId: Int,
        @SerializedName("provider_name")
        val providerName: String,
        @SerializedName("display_priority")
        val displayPriority: Int
    )
    {
        internal fun toMovieProvider() = MovieProvider(
            logoPath = logoPath,
            providerId = providerId,
            providerName = providerName,
            displayPriority = displayPriority,
        )
    }
}