package com.gmail.eamosse.idbdata.api.request

import com.gmail.eamosse.idbdata.api.response.TokenResponse
import com.gmail.eamosse.idbdata.data.RatingBody
import com.gmail.eamosse.idbdata.local.entities.TokenEntity
import com.google.gson.annotations.SerializedName

internal data class RatingBodyRequest(
    @SerializedName("value")
    val value: Double
)