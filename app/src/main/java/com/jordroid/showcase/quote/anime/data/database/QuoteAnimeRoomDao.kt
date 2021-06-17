package com.jordroid.showcase.quote.anime.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteAnimeRoomDao {

    @Query("SELECT * FROM quote_anime_entity")
    fun selectAll(): Flow<List<QuoteAnimeRoom>>

    @Query("SELECT * FROM quote_anime_entity WHERE anime_name= :animeName")
    fun findByAnimeName(animeName: String): QuoteAnimeRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(quoteAnimeRoomList: List<QuoteAnimeRoom>)

}
