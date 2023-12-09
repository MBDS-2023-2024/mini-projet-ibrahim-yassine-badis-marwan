package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Movie
import com.google.gson.annotations.SerializedName

internal data class PopularMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Items>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
){
    data class Items(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
    ){
        internal fun toMoviePop() = com.gmail.eamosse.idbdata.data.Movie(
            adult = adult,
            backdropPath = backdropPath ?: "7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg",
            id = id.toLong(),
            title = title,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            posterPath = posterPath,
            popularity = popularity,
            releaseDate = releaseDate,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            mediaType = "")
    }
}



