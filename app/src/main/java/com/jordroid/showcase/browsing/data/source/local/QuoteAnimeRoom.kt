package com.jordroid.showcase.browsing.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quote_anime_entity",
    indices = [Index(value = ["anime_name"], unique = true)]
)
data class QuoteAnimeRoom(

    @ColumnInfo(name = "anime_name")
    val animeName: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)