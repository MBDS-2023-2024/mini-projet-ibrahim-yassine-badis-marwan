package com.gmail.eamosse.idbdata.data

import com.gmail.eamosse.idbdata.local.entities.FavoriteMovieEntity
import com.google.gson.annotations.SerializedName

data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val id: Long,
    val title: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val mediaType: String?,
    //val genreIds: List<Int>,
    val popularity: Double,
    val releaseDate: String?,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
){

}