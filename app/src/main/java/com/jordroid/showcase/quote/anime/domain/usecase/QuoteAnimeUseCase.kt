package com.jordroid.showcase.quote.anime.domain.usecase

import com.jordroid.showcase.quote.anime.domain.model.QuoteAnimeEntity
import com.jordroid.showcase.quote.anime.domain.repository.QuoteAnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteAnimeUseCase(
    private val quoteAnimeRepository: QuoteAnimeRepository
) {

    fun readAll(): Flow<List<QuoteAnimeEntity>> = quoteAnimeRepository.readAll().map { list ->
        list.sortedBy {
            it.animeName
        }.filter {
            it.animeName.isNotEmpty()
        }
    }

    suspend fun fetch() {
        quoteAnimeRepository.fetchAnimeData()
    }
}
