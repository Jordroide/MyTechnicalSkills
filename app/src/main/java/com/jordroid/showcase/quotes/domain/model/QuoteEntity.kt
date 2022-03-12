package com.jordroid.showcase.quotes.domain.model

/**
 * Entity quote object
 */
data class QuoteEntity(
    val id : Long,
    val anime: String,
    val character: String,
    val quote: String
)
