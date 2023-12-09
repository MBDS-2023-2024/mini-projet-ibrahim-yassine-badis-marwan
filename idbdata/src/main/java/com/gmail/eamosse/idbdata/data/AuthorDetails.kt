package com.gmail.eamosse.idbdata.data

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    val name: String?,
    val username: String,
    val avatarPath: String?,
    val rating: Int?
)
