package com.jordroid.showcase.quote.domain.usecase

import com.jordroid.showcase.quote.domain.model.QuoteEntity
import com.jordroid.showcase.quote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class QuoteUseCase(private val quoteRepository: QuoteRepository) {

    private val searchFlow = MutableStateFlow("")

    fun readAll(): Flow<List<QuoteEntity>> =
        quoteRepository.readAll().combine(searchFlow) { list, search ->
            list.sortedBy {
                it.anime
            }.filter { item ->
                item.anime.contains(search, ignoreCase = true)
            }
        }

    suspend fun searchWith(textValue: String) {
        searchFlow.emit(textValue)
    }

    suspend fun fetch() {
        quoteRepository.fetchData()
    }
}
