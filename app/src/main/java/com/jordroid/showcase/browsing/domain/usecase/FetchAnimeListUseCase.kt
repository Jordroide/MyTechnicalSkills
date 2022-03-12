package com.jordroid.showcase.browsing.domain.usecase

import com.jordroid.showcase.browsing.domain.repository.AnimeRepository

class FetchAnimeListUseCase(private val animeRepository: AnimeRepository) {

    /**
     * Get the list from data source
     */
    suspend fun fetchAnimeList() {
        animeRepository.fetchAnimeData()
    }
}
