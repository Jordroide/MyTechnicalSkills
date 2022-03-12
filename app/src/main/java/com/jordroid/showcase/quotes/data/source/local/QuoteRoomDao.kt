package com.jordroid.showcase.quotes.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteRoomDao {

    @Query("SELECT * FROM quote_entity WHERE quote = :quote")
    fun selectByQuote(quote : String): QuoteRoom?

    @Query("SELECT * FROM quote_entity")
    fun selectAll(): Flow<List<QuoteRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quoteRoom: QuoteRoom)

    @Query("DELETE FROM quote_entity WHERE id = :id")
    fun delete(id : Long)

    @Query("DELETE FROM quote_entity")
    fun deleteAll()
}
