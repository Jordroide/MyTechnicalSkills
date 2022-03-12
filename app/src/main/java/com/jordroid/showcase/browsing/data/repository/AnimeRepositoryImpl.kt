package com.jordroid.showcase.browsing.data.repository

import com.jordroid.showcase.browsing.data.source.local.QuoteAnimeRoom
import com.jordroid.showcase.browsing.data.source.local.QuoteAnimeRoomDao
import com.jordroid.showcase.browsing.data.source.remote.QuoteAnimeApi
import com.jordroid.showcase.browsing.domain.model.AnimeEntity
import com.jordroid.showcase.browsing.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeRepositoryImpl(
    private val quoteAnimeApi: QuoteAnimeApi,
    private val quoteAnimeRoomDao: QuoteAnimeRoomDao,
) : AnimeRepository {

    override fun readAll(): Flow<List<AnimeEntity>> =
        quoteAnimeRoomDao.selectAll().map { list ->
            list.map {
                it.toEntity()
            }
        }

    override suspend fun fetchAnimeData() {
        if (quoteAnimeRoomDao.countElement() == 0) {
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
    }
}

private fun List<String>.toRoom(): List<QuoteAnimeRoom> {
    return map { animeName ->
        QuoteAnimeRoom(animeName)
    }
}

private fun QuoteAnimeRoom.toEntity() = AnimeEntity(
    animeName = animeName
)
