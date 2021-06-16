package com.jordroid.showcase.quote.data.localdatasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteRoomDao {

    @Query("SELECT * FROM quote_entity WHERE quote = :quote")
    fun readByQuote(quote : String): QuoteRoom?

    @Query("SELECT * FROM quote_entity")
    fun readAll(): Flow<List<QuoteRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quoteRoom: QuoteRoom)

    @Query("DELETE FROM quote_entity")
    fun deleteAll()
}
