package com.jordroid.showcase.quotes.domain.usecase

import com.jordroid.showcase.quotes.domain.repository.QuoteRepository

class FetchNewQuoteUseCase(private val quoteRepository: QuoteRepository) {

    /**
     * Fetch one new quote from online
     */
    suspend fun fetch() {
        quoteRepository.fetchData()
    }
}
