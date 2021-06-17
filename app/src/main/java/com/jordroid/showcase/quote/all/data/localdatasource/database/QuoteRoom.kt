package com.jordroid.showcase.quote.all.data.localdatasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "quote_entity"
)
data class QuoteRoom(

    @ColumnInfo(name = "anime")
    val anime: String,

    @ColumnInfo(name = "character")
    val character: String,

    @ColumnInfo(name = "quote")
    val quote: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)
