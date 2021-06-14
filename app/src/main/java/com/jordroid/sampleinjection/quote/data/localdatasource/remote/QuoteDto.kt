package com.jordroid.sampleinjection.quote.data.localdatasource.remote

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("value")
    val value: Value
)

data class Value(

    @SerializedName("id")
    val id : Long,

    @SerializedName("joke")
    val content: String,

    @SerializedName("categories")
    val category: List<String>
)