package com.jordroid.showcase.gallery.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jordroid.showcase.gallery.domain.model.Doggo
import com.jordroid.showcase.gallery.domain.usecase.GetDoggoUseCase
import com.jordroid.showcase.gallery.presenter.model.DoggoUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GalleryDoggoViewModel(private val getDoggoUseCase: GetDoggoUseCase) : ViewModel() {

    fun fetchDoggoImages(): Flow<PagingData<DoggoUi>> {
        return getDoggoUseCase.load().flowOn(Dispatchers.IO).map {
            it.toUi()
        }.cachedIn(viewModelScope)
    }
}

/**
 * Map entity to ui object
 */
private fun Doggo.toUi() = DoggoUi(
    url = url
)

private fun PagingData<Doggo>.toUi() : PagingData<DoggoUi> {
    return map { doggo ->
        doggo.toUi()
    }
}