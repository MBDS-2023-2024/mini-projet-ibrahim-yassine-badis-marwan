package com.gmail.eamosse.idbdata.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "idb_favoriteMovies")
class FavoriteMovieEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val mediaType: String,
    //val genreIds: String,
    val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,

){

}