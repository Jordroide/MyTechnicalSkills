package com.jordroid.showcase.gallery.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DoggoDataSource(

    @SerializedName("url")
    val wikipediaUrl : String
)
