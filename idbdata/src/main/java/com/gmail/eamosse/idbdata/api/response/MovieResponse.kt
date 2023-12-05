package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.Movie
import com.google.gson.annotations.SerializedName

internal data class MovieResponse(
    @SerializedName("items")
    val items: List<Item>
) {
    data class Item(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int,
        ) {
        internal fun toMovie() = Movie(
             adult = adult ,
        backdropPath =  backdropPath?: "7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg",
         id = id.toLong(),
         title =  title,
         originalLanguage =  originalLanguage,
         originalTitle = originalTitle,
         overview = overview,
         posterPath = posterPath ,
         mediaType =  mediaType,
         //genreIds =  genreIds,
         popularity =  popularity,
         releaseDate = releaseDate,
         video = video,
         voteAverage = voteAverage,
         voteCount =  voteCount
        )
    }

}