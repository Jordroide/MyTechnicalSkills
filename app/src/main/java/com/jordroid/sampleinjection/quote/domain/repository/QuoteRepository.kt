package com.jordroid.sampleinjection.quote.domain.repository

import com.jordroid.sampleinjection.quote.data.localdatasource.database.QuoteRoom
import com.jordroid.sampleinjection.quote.data.localdatasource.database.QuoteRoomDao
import com.jordroid.sampleinjection.quote.data.localdatasource.remote.QuoteApi
import com.jordroid.sampleinjection.quote.data.localdatasource.remote.QuoteDto
import com.jordroid.sampleinjection.quote.domain.model.QuoteEntity
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
                    quoteDto?.let { quoteDto ->
                        quoteRoomDao.insert(quoteRoomDao.readById(quoteDto.value.id)?.let {
                            quoteDto.toRoom().copy(id = it.id)
                        } ?: quoteDto.toRoom())
                    }
                }
            }
        }
    }

    private fun QuoteDto.toRoom() = QuoteRoom(
        content = value.content,
        category = if (value.category.isNotEmpty()) value.category[0] else "default",
        id = value.id
    )

    private fun QuoteRoom.toEntity() = QuoteEntity(quote = content, category = category)
}