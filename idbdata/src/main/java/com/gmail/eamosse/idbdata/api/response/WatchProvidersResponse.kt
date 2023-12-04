package com.gmail.eamosse.idbdata.api.response

import com.google.gson.annotations.SerializedName

data class WatchProvidersResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results:  Map<String, CountryResult>
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
            link = link.orEmpty(),
            buy = buy.orEmpty().map { it.toMovieProvider() },
            rent = rent.orEmpty().map { it.toMovieProvider() },
            flatrate = flatrate.orEmpty().map { it.toMovieProvider() }
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