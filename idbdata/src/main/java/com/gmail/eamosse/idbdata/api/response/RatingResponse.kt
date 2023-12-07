package com.gmail.eamosse.idbdata.api.response

import com.gmail.eamosse.idbdata.data.Rating
import com.gmail.eamosse.idbdata.data.Trailer
import com.google.gson.annotations.SerializedName

internal data class RatingResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,

){
    internal fun toRating() = Rating(
        success = success,
        status_code = statusCode,
        status_message = statusMessage
    )
}
