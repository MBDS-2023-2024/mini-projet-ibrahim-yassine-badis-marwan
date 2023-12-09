package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Serie
import com.gmail.eamosse.idbdata.datasources.toFavoriteSerie
import com.google.gson.annotations.SerializedName

data class SerieResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val items: List<Item>,

    @SerializedName("total_pages")
    val totalPages:Int,

    @SerializedName("total_results")
    val totalResults:Int


) {
    data class Item(
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("first_air_date")
        val firstAirDate: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("origin_country")
        val originCountry: List<String>,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int,
    ) {
        internal fun toSerie() = Serie(
            firstAirDate =  this.firstAirDate,
            originCountry = this.originCountry[0],
            backdropPath = this.backdropPath,
            id =  this.id.toLong(),
            originalName =  this.originalName,
            originalLanguage = this.originalLanguage.toString(),
            overview =  this.overview.toString(),
            posterPath = this.posterPath?: "7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg" ,
            popularity = this.popularity.toString(),
            voteCount = this.voteCount,
            name = this.name,
            voteAverage = this.voteAverage.toInt()
        )
    }
}

