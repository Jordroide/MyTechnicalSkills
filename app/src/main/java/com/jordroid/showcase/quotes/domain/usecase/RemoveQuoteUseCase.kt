package com.jordroid.showcase.quotes.domain.usecase

import com.jordroid.showcase.quotes.domain.repository.QuoteRepository

class RemoveQuoteUseCase(private val quoteRepository: QuoteRepository) {

    /**
     * Fetch one new quote from online
     */
    suspend fun delete(id : Long) {
        quoteRepository.delete(id)
    }
}
