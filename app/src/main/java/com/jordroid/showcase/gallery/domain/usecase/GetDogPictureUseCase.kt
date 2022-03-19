package com.jordroid.showcase.gallery.domain.usecase

import androidx.paging.PagingData
import com.jordroid.showcase.gallery.domain.model.DogPicture
import com.jordroid.showcase.gallery.domain.repository.GalleryDogRepository
import kotlinx.coroutines.flow.Flow

class GetDogPictureUseCase(val repository: GalleryDogRepository) {

    fun load(): Flow<PagingData<DogPicture>> = repository.getDoggoImages()
}
