package com.jordroid.showcase.gallery.di

import com.jordroid.showcase.application.remote.RetrofitClient
import com.jordroid.showcase.gallery.data.remote.DoggoPagingSource
import com.jordroid.showcase.gallery.data.remote.DoggyApi
import com.jordroid.showcase.gallery.data.repository.DoggoRepositoryImpl
import com.jordroid.showcase.gallery.domain.repository.DoggoRepository
import com.jordroid.showcase.gallery.domain.usecase.GetDoggoUseCase
import com.jordroid.showcase.gallery.presenter.viewmodel.GalleryDoggoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val doggoGalleryModule = module {
    viewModel {
        GalleryDoggoViewModel(
            getDoggoUseCase = get()
        )
    }

    single { GetDoggoUseCase(repository = get()) }
    single<DoggoRepository> { DoggoRepositoryImpl(doggoApiService = get()) }
    single { DoggoPagingSource(doggyApi = get()) }

    single { get<Retrofit>().create(DoggyApi::class.java) }

    single { RetrofitClient.getRetrofitDogsInstance() }
}