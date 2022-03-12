package com.jordroid.showcase.quotes.domain.repository

import com.jordroid.showcase.quotes.domain.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    /**
     * Get the list of quotes stored in database
     */
    fun readAll(): Flow<List<QuoteEntity>>

    /**
     * Delete the given quote from it's id
     */
    suspend fun delete(id : Long)

    /**
     * Get new quote from api
     */
    suspend fun fetchData()
}
