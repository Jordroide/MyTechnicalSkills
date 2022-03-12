package com.jordroid.showcase.quotes.di

import com.jordroid.showcase.application.database.QuoteDatabase
import com.jordroid.showcase.quotes.data.repository.QuoteRepositoryImpl
import com.jordroid.showcase.quotes.data.source.remote.QuoteApi
import com.jordroid.showcase.quotes.domain.repository.QuoteRepository
import com.jordroid.showcase.quotes.domain.usecase.FetchNewQuoteUseCase
import com.jordroid.showcase.quotes.domain.usecase.GetQuotesUseCase
import com.jordroid.showcase.quotes.domain.usecase.RemoveQuoteUseCase
import com.jordroid.showcase.quotes.presenter.viewmodel.QuoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val quoteModule = module {
    viewModel {
        QuoteViewModel(
            getQuotesUseCase = get(),
            fetchNewQuoteUseCase = get(),
            removeQuoteUseCase = get()
        )
    }

    /// UseCase
    single { GetQuotesUseCase(quoteRepository = get()) }
    single { FetchNewQuoteUseCase(quoteRepository = get()) }
    single { RemoveQuoteUseCase(quoteRepository = get()) }

    /// Repository
    single<QuoteRepository> { QuoteRepositoryImpl(quoteApi = get(), quoteRoomDao = get()) }

    // DAO
    single { get<QuoteDatabase>().quoteDao() }

    // Retrofit
    single { get<Retrofit>().create(QuoteApi::class.java) }
}