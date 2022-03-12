package com.jordroid.showcase.browsing.presenter.model

/**
 * Anime ui sealed class to handle item and header item
 */
sealed class AnimeUi(open val label: String) {
    /**
     * Content of anime
     */
    data class AnimeItemUi(private val animeName: String) : AnimeUi(label = animeName)

    /**
     * Header for anime with same first letter
     */
    data class AnimeHeaderUi(
        private val title: String,
        val amount: Int
    ) : AnimeUi(label = title)
}
