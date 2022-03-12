package com.jordroid.showcase.quotes.presenter.model

/**
 * Quote statistics ui object
 */
data class QuoteStatisticUi(
    val numberItem: Int,
    val numberDistinctAnime: Int
)

/**
 * Quote ui sealed class to handle item and header item
 */
sealed class QuoteUi(open val label: String) {

    /**
     * Content of quote
     */
    data class QuoteItemUi(
        private val anime: String,
        val id : Long,
        val character: String,
        val quote: String
    ) : QuoteUi(label = anime)

    /**
     * Header for quote with same anime name
     */
    data class QuoteHeaderUi(
        val animeName: String
    ) : QuoteUi(label = animeName)
}
