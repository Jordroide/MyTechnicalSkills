package com.jordroid.showcase.quote.all.data.localdatasource.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class QuoteDto(
    @SerializedName("anime")
    val anime: String,

    @SerializedName("character")
    val character: String,

    @SerializedName("quote")
    val quote: String
)
