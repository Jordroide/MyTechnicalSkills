package com.jordroid.showcase.gallery.di

import com.jordroid.showcase.gallery.data.remote.DoggyApi
import com.jordroid.showcase.gallery.data.remote.DoggyPagingSource
import com.jordroid.showcase.gallery.data.repository.GalleryDogRepositoryImpl
import com.jordroid.showcase.gallery.domain.repository.GalleryDogRepository
import com.jordroid.showcase.gallery.domain.usecase.GetDogPictureUseCase
import com.jordroid.showcase.gallery.presenter.viewmodel.GalleryDoggoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val doggoGalleryModule = module {
    // ViewModel
    viewModel {
        GalleryDoggoViewModel(
            getDogPictureUseCase = get()
        )
    }

    // UseCase
    single { GetDogPictureUseCase(repository = get()) }
    // Repository
    single<GalleryDogRepository> { GalleryDogRepositoryImpl(doggoApiService = get()) }
    // PagingSource
    single { DoggyPagingSource(doggyApi = get()) }
    //Retrofit
    single { get<Retrofit>().create(DoggyApi::class.java) } }