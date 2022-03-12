package com.jordroid.showcase.quotes.domain.usecase

import com.jordroid.showcase.quotes.domain.model.QuoteEntity
import com.jordroid.showcase.quotes.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GetQuotesUseCase(private val quoteRepository: QuoteRepository) {

    /**
     * Flow that contain the value of user search
     */
    private val searchFlow = MutableStateFlow("")

    /**
     * Get the list of [QuoteEntity] and apply domain requirement
     * First filter all anime by matching search text
     * Then sort by anime name ASC
     */
    fun readAll(): Flow<List<QuoteEntity>> =
        quoteRepository.readAll().combine(searchFlow) { list, filterCharacter ->
            list.asSequence().filter { item ->
                item.anime.contains(filterCharacter, ignoreCase = true)
            }.sortedBy {
                it.anime
            }.toList()
        }

    /**
     * Update search flow content by new value
     */
    suspend fun searchAnimeWithName(value: String) {
        searchFlow.emit(value)
    }
}
