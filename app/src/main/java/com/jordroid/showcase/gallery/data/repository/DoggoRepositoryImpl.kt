package com.jordroid.showcase.gallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jordroid.showcase.gallery.data.model.DoggoDataSource
import com.jordroid.showcase.gallery.data.remote.DoggoPagingSource
import com.jordroid.showcase.gallery.data.remote.DoggyApi
import com.jordroid.showcase.gallery.domain.model.Doggo
import com.jordroid.showcase.gallery.domain.repository.DoggoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DoggoRepositoryImpl(
    val doggoApiService: DoggyApi
) : DoggoRepository {

    /**
     * calling the paging source to give results from api calls
     * and returning the results in the form of flow [Flow<PagingData<DoggoDataSource>>]
     */
    override fun getDoggoImages(): Flow<PagingData<Doggo>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = true),
            pagingSourceFactory = { DoggoPagingSource(doggoApiService) }
        ).flow.map {
            it.map { doggo ->
                doggo.toEntity()
            }
        }
    }
}

/**
 * Map Data source to entity object
 */
private fun DoggoDataSource.toEntity() = Doggo(
    url = wikipediaUrl
)
