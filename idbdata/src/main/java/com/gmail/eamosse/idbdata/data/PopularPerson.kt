package com.gmail.eamosse.idbdata.data

data class PopularPerson(
    val id: Int,
    val knownForDepartment: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val moviesId: List<Int>
)
