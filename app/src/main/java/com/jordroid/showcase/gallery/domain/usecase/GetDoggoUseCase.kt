package com.jordroid.showcase.gallery.domain.usecase

import androidx.paging.PagingData
import com.jordroid.showcase.gallery.domain.model.Doggo
import com.jordroid.showcase.gallery.domain.repository.DoggoRepository
import kotlinx.coroutines.flow.Flow

class GetDoggoUseCase(val repository: DoggoRepository) {

    fun load(): Flow<PagingData<Doggo>> = repository.getDoggoImages()
}
