package com.gmail.eamosse.idbdata.data



import com.gmail.eamosse.idbdata.local.entities.FavoriteMovieEntity
import com.google.gson.annotations.SerializedName

data class Serie(
    val backdropPath: String?,
    val firstAirDate : String,
   // val genreIds :  List<Int>,
    val id: Long ,
    val name :String,
    val originCountry : String,
    val originalLanguage :String,
    val originalName: String,
    val overview: String,
    val popularity : String,
    val posterPath : String?,
    val voteAverage : Int,
    val voteCount : Int,
){

}