package com.gmail.eamosse.idbdata.data

data class Review(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAt: String,
    val reviewId: String,
    val updatedAt: String,
    val url: String
)
