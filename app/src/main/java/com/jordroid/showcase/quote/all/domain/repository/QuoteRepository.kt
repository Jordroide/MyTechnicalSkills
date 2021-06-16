package com.jordroid.showcase.quote.all.domain.repository

import com.jordroid.showcase.quote.all.data.localdatasource.database.QuoteRoom
import com.jordroid.showcase.quote.all.data.localdatasource.database.QuoteRoomDao
import com.jordroid.showcase.quote.all.data.localdatasource.remote.QuoteApi
import com.jordroid.showcase.quote.all.data.localdatasource.remote.QuoteDto
import com.jordroid.showcase.quote.all.domain.model.QuoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit

class QuoteRepository(
    private val quoteRoomDao: QuoteRoomDao,
    private val retrofit: Retrofit
) {

    fun readAll(): Flow<List<QuoteEntity>> = quoteRoomDao.readAll().map { list ->
        list.map {
            it.toEntity()
        }
    }

    suspend fun fetchData() {
        with(retrofit.create(QuoteApi::class.java).getRandomQuote()) {
            if (isSuccessful) {
                body()?.let {
                    val quoteDto: QuoteDto? = body()
                    quoteDto?.let { item ->
                        quoteRoomDao.insert(quoteRoomDao.readByQuote(item.quote)?.let {
                            item.toRoom().copy(id = it.id)
                        } ?: item.toRoom())
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
}
