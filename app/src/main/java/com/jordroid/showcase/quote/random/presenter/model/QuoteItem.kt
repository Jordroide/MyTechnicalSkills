package com.jordroid.showcase.quote.random.presenter.model

data class QuoteStatistic(
    val numberItem: Int,
    val numberDistinctAnime: Int
)

sealed class QuoteUi(open val label: String) {

    data class QuoteItemUi(
        private val anime: String,
        val character: String,
        val quote: String
    ) : QuoteUi(label = anime)

    data class QuoteHeaderUi(
        val animeName: String
    ) : QuoteUi(label = animeName)
}
