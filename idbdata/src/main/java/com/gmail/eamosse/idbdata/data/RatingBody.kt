package com.gmail.eamosse.idbdata.data

import com.gmail.eamosse.idbdata.api.request.RatingBodyRequest

data class RatingBody(
    val value: Double
)   {
    internal fun toRatingBodyEntity() = RatingBodyRequest(
        value = value
    )
}