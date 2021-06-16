package com.jordroid.showcase.quote.anime.domain.repository

import com.jordroid.showcase.quote.anime.data.database.QuoteAnimeRoom
import com.jordroid.showcase.quote.anime.data.database.QuoteAnimeRoomDao
import com.jordroid.showcase.quote.anime.data.remote.QuoteAnimeApi
import com.jordroid.showcase.quote.anime.domain.model.QuoteAnimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteAnimeRepository(
    private val quoteAnimeApi: QuoteAnimeApi,
    private val quoteAnimeRoomDao: QuoteAnimeRoomDao,
) {

    fun readAll(): Flow<List<QuoteAnimeEntity>> = quoteAnimeRoomDao.readAll().map { list ->
        list.map {
            it.toEntity()
        }
    }

    suspend fun fetchAnimeData() {
        with(quoteAnimeApi.getAllAnime()) {
            if (isSuccessful) {
                body()?.let {
                    val quoteAnimeDto: List<String>? = body()
                    quoteAnimeDto?.let { list ->
                        quoteAnimeRoomDao.insertAll(list.toRoom())
                    }
                }
            }
        }
    }

    private fun List<String>.toRoom(): List<QuoteAnimeRoom> {
        return map { animeName ->
            QuoteAnimeRoom(animeName)
        }
    }

    private fun QuoteAnimeRoom.toEntity() = QuoteAnimeEntity(
        animeName = animeName
    )
}
