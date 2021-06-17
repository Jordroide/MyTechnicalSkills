package com.jordroid.showcase.quote.all.domain.repository

import com.jordroid.showcase.quote.all.data.localdatasource.database.QuoteRoom
import com.jordroid.showcase.quote.all.data.localdatasource.database.QuoteRoomDao
import com.jordroid.showcase.quote.all.data.localdatasource.remote.QuoteApi
import com.jordroid.showcase.quote.all.data.localdatasource.remote.QuoteDto
import com.jordroid.showcase.quote.all.domain.model.QuoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteRepository(
    private val quoteApi: QuoteApi,
    private val quoteRoomDao: QuoteRoomDao
) {

    fun readAll(): Flow<List<QuoteEntity>> = quoteRoomDao.selectAll().map { list ->
        list.map {
            it.toEntity()
        }
    }

    suspend fun fetchData() {
        with(quoteApi.getRandomQuote()) {
            if (isSuccessful) {
                body()?.let {
                    val quoteDto: QuoteDto? = body()
                    quoteDto?.let { item ->
                        quoteRoomDao.insert(quoteRoomDao.selectByQuote(item.quote)?.let {
                            item.toRoom().copy(id = it.id)
                        } ?: item.toRoom())
                    }
                }
            }
        }
    }
}

private fun QuoteDto.toRoom() = QuoteRoom(
    anime = anime,
    character = character,
    quote = quote
)

private fun QuoteRoom.toEntity() = QuoteEntity(
    anime = anime,
    character = character,
    quote = quote
)
