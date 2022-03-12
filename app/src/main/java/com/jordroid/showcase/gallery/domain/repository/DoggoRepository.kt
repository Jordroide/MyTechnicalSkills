package com.jordroid.showcase.gallery.domain.repository

import androidx.paging.PagingData
import com.jordroid.showcase.gallery.domain.model.Doggo
import kotlinx.coroutines.flow.Flow

interface DoggoRepository {

    fun getDoggoImages(): Flow<PagingData<Doggo>>
}