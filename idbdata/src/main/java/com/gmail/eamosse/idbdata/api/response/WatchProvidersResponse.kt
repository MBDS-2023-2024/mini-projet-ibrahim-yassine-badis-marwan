package com.gmail.eamosse.idbdata.api.response

import com.google.gson.annotations.SerializedName

data class WatchProvidersResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results:  Map<String, CountryResult>
){


    data class Country(
    @SerializedName("AD")
    val AD: List<CountryResult>
)
{
   /*
    internal fun toCountry() = Country(
        AD = AD.orEmpty().map { it.toCountryResult() },
    )

    */
}
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
        internal fun toCountryResult() = com.gmail.eamosse.idbdata.data.MovieProviderPackage.CountryResult(
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
        internal fun toMovieProvider() = com.gmail.eamosse.idbdata.data.MovieProvider(
            logoPath = logoPath,
            providerId = providerId,
            providerName = providerName,
            displayPriority = displayPriority,
        )
    }

}
