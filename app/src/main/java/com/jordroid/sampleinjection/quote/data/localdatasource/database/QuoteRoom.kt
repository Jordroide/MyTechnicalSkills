package com.jordroid.sampleinjection.quote.data.localdatasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "quote_entity"
)
data class QuoteRoom(

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "category")
    val category: String,

    @PrimaryKey(autoGenerate = false)
    var id: Long? = null
)
