package com.jordroid.showcase.gallery.domain.repository

import androidx.paging.PagingData
import com.jordroid.showcase.gallery.domain.model.DogPicture
import kotlinx.coroutines.flow.Flow

interface GalleryDogRepository {

    /**
     * Get dog pictures from data source as paging data
     */
    fun getDoggoImages(): Flow<PagingData<DogPicture>>
}