package com.jordroid.sampleinjection.quote.domain.usecase

import com.jordroid.sampleinjection.quote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.map

class QuoteUseCase(private val quoteRepository: QuoteRepository) {

    fun readAll() = quoteRepository.readAll().map { list ->
        list.sortedBy { it.quote }
    }

    suspend fun fetch() {
        quoteRepository.fetchData()
    }
}
