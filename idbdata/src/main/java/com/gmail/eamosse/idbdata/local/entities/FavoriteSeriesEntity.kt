package com.gmail.eamosse.idbdata.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "idb_favoriteSeries")
class FavoriteSeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long ,
    val backdropPath: String?,
    val firstAirDate : String,
    val name :String,
    val originCountry : String,
    val originalLanguage :String,
    val originalName: String,
    val overview: String,
    val popularity : String,
    val posterPath : String?,
    val voteAverage : Int,
    val voteCount : Int,
) {

}