package com.jordroid.showcase.browsing.domain.repository

import com.jordroid.showcase.browsing.domain.model.AnimeEntity
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    /**
     * Get all anime from data source
     */
    fun readAll(): Flow<List<AnimeEntity>>

    /**
     * Get anime list from data source
     */
    suspend fun fetchAnimeData()
}
