package com.jordroid.showcase.quotes.data.repository

import com.jordroid.showcase.quotes.data.source.local.QuoteRoom
import com.jordroid.showcase.quotes.data.source.local.QuoteRoomDao
import com.jordroid.showcase.quotes.data.source.remote.QuoteApi
import com.jordroid.showcase.quotes.data.source.remote.QuoteDto
import com.jordroid.showcase.quotes.domain.model.QuoteEntity
import com.jordroid.showcase.quotes.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteRepositoryImpl(
    private val quoteApi: QuoteApi,
    private val quoteRoomDao: QuoteRoomDao
) : QuoteRepository {

    override fun readAll(): Flow<List<QuoteEntity>> = quoteRoomDao.selectAll().map { list ->
        list.map {
            it.toEntity()
        }
    }

    // TODO improve error case from retrofit
    override suspend fun fetchData() {
        with(quoteApi.getRandomQuote()) {
            if (isSuccessful) {
                body()?.let {
                    val quoteDto: QuoteDto? = body()
                    quoteDto?.let { item ->
                        // If item is already present then update content and insert then create new object
                        quoteRoomDao.insert(quoteRoomDao.selectByQuote(item.quote)?.let {
                            item.toRoom().copy(id = it.id)
                        } ?: item.toRoom())
                    }
                }
            }
        }
    }

    override suspend fun delete(id: Long) {
        quoteRoomDao.delete(id)
    }
}

/**
 * Map [QuoteDto] to room object [QuoteRoom]
 */
private fun QuoteDto.toRoom() = QuoteRoom(
    anime = anime,
    character = character,
    quote = quote
)

/**
 * Map [QuoteEntity] to entity object [QuoteEntity]
 */
private fun QuoteRoom.toEntity() = QuoteEntity(
    id = id ?: 0,
    anime = anime,
    character = character,
    quote = quote
)
