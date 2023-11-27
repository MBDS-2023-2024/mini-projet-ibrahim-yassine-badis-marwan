package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Movie
import com.gmail.eamosse.idbdata.data.Trailer
import com.google.gson.annotations.SerializedName

internal data class MovieTrailerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>,
){
    data class Result(
        @SerializedName("iso_639_1")
        val iso_639_1: String,
        @SerializedName("iso_3166_1")
        val iso_3166_1: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("site")
        val site: String,
        @SerializedName("size")
        val size: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("official")
        val official: Boolean,
        @SerializedName("published_at")
        val published_at: String,
        @SerializedName("id")
        val id: String,
    ){
        internal fun toMovieTrailer() = Trailer(
            iso6391= iso_639_1,
            iso31661= iso_3166_1,
            name= name,
            key= key,
            site= site,
            size= size,
            type= type,
            official= official,
            publishedAt= published_at,
            id= id
        )
    }
}