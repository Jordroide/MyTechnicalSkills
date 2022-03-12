package com.jordroid.showcase.browsing.di

import com.jordroid.showcase.application.database.QuoteDatabase
import com.jordroid.showcase.browsing.data.repository.AnimeRepositoryImpl
import com.jordroid.showcase.browsing.data.source.remote.QuoteAnimeApi
import com.jordroid.showcase.browsing.domain.repository.AnimeRepository
import com.jordroid.showcase.browsing.domain.usecase.FetchAnimeListUseCase
import com.jordroid.showcase.browsing.domain.usecase.GetAnimeListUseCase
import com.jordroid.showcase.browsing.presenter.viewmodel.BrowsingAnimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val browsingModule = module {
    // ViewModel
    viewModel { BrowsingAnimeViewModel(getAnimeListUseCase = get(), fetchAnimeListUseCase = get()) }
    /// UseCase
    single { GetAnimeListUseCase(animeRepository = get()) }
    single { FetchAnimeListUseCase(animeRepository = get()) }
    /// Repository
    single<AnimeRepository> {
        AnimeRepositoryImpl(
            quoteAnimeApi = get(),
            quoteAnimeRoomDao = get()
        )
    }
    /// Dao
    single { get<QuoteDatabase>().quoteAnimeDao() }
    /// Retrofit
    single { get<Retrofit>().create(QuoteAnimeApi::class.java) }
}