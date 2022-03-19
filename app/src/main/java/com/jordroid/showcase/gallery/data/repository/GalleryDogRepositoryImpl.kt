package com.jordroid.showcase.gallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jordroid.showcase.gallery.data.model.DogPictureDataSource
import com.jordroid.showcase.gallery.data.remote.DoggyPagingSource
import com.jordroid.showcase.gallery.data.remote.DoggyApi
import com.jordroid.showcase.gallery.domain.model.DogPicture
import com.jordroid.showcase.gallery.domain.repository.GalleryDogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GalleryDogRepositoryImpl(
    val doggoApiService: DoggyApi
) : GalleryDogRepository {

    /**
     * calling the paging source to give results from api calls
     * and returning the results in the form of flow [Flow<PagingData<DoggoDataSource>>]
     */
    override fun getDoggoImages(): Flow<PagingData<DogPicture>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = true),
            pagingSourceFactory = { DoggyPagingSource(doggoApiService) }
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
private fun DogPictureDataSource.toEntity() = DogPicture(
    url = wikipediaUrl
)
