package com.gmail.eamosse.idbdata.api.request

import com.google.gson.annotations.SerializedName

data class RatingBody(
    @SerializedName("value")
    val value: Double
)