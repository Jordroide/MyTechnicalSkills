package com.jordroid.showcase.browsing.domain.usecase

import com.jordroid.showcase.browsing.domain.model.AnimeEntity
import com.jordroid.showcase.browsing.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAnimeListUseCase(
    private val animeRepository: AnimeRepository
) {

    /**
     * Get the list of all anime and apply domain requirement
     * First remove empty string
     * Then sort by anime name ASC
     */
    fun readAll(): Flow<List<AnimeEntity>> = animeRepository.readAll().map { list ->
        list.filter {
            it.animeName.isNotEmpty()
        }.sortedBy {
            it.animeName
        }
    }
}
