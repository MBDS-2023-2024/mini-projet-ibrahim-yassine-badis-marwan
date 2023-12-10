package com.gmail.eamosse.idbdata.data



import com.gmail.eamosse.idbdata.local.entities.FavoriteMovieEntity
import com.google.gson.annotations.SerializedName

data class Serie(
    val backdropPath: String? = null,
    val firstAirDate: String = "",
    val id: Long = 0,
    val name: String = "",
    val originCountry: String = "",
    val originalLanguage: String = "",
    val originalName: String = "",
    val overview: String = "",
    val popularity: String = "",
    val posterPath: String? = null,
    val voteAverage: Int = 0,
    val voteCount: Int = 0
)
