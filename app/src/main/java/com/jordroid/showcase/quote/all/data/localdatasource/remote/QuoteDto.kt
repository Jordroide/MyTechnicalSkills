package com.jordroid.showcase.quote.all.data.localdatasource.remote

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("anime")
    val anime: String,

    @SerializedName("character")
    val character: String,

    @SerializedName("quote")
    val quote: String
)
