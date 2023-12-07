package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Category
import com.gmail.eamosse.idbdata.data.PopularPerson
import com.google.gson.annotations.SerializedName

internal data class PopularPersonResponse(

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<Item>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
) {
    data class Item(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("profile_path")
        val profilePath: String?,
        @SerializedName("known_for")
        val knownFor: List<KnownFor>
    ) {
        data class KnownFor(
            @SerializedName("adult")
            val adult: Boolean,
            @SerializedName("backdrop_path")
            val backdropPath: String?,
            @SerializedName("id")
            val id: Int,
            @SerializedName("title")
            val title: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("original_language")
            val originalLanguage: String,
            @SerializedName("original_title")
            val originalTitle: String?,
            @SerializedName("overview")
            val overview: String,
            @SerializedName("poster_path")
            val posterPath: String?,
            @SerializedName("media_type")
            val mediaType: String,
            @SerializedName("genre_ids")
            val genreIds: List<Int>,
            @SerializedName("popularity")
            val popularity: Double,
            @SerializedName("release_date")
            val releaseDate: String?,
            @SerializedName("first_air_date")
            val firstAirDate: String?,
            @SerializedName("vote_average")
            val voteAverage: Double,
            @SerializedName("vote_count")
            val voteCount: Int,
            @SerializedName("origin_country")
            val originCountry: List<String>?
        )

        // Convertit un PopularPerson en Category (si c'est ce que vous souhaitez).
        // Assurez-vous que la classe Category peut accepter toutes les données nécessaires.
        internal fun toPopularPerson() = PopularPerson(
            id = id,
            knownForDepartment = knownForDepartment,
            originalName = originalName,
            popularity = popularity,
            profilePath = profilePath,
            moviesId = knownFor.map { it.id }
        )
    }
}
