package com.jordroid.showcase.gallery.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jordroid.showcase.gallery.domain.model.DogPicture
import com.jordroid.showcase.gallery.domain.usecase.GetDogPictureUseCase
import com.jordroid.showcase.gallery.presenter.model.DogPictureUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GalleryDoggoViewModel(private val getDogPictureUseCase: GetDogPictureUseCase) : ViewModel() {

    fun fetchDoggoImages(): Flow<PagingData<DogPictureUi>> {
        return getDogPictureUseCase.load().flowOn(Dispatchers.IO).map {
            it.toUi()
        }.cachedIn(viewModelScope)
    }
}

/**
 * Map entity to ui object
 */
private fun DogPicture.toUi() = DogPictureUi(
    url = url
)

private fun PagingData<DogPicture>.toUi() : PagingData<DogPictureUi> {
    return map { doggo ->
        doggo.toUi()
    }
}