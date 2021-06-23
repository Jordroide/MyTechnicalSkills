package com.jordroid.showcase.application

import android.app.Application
import com.jordroid.showcase.application.database.QuoteDatabase
import com.jordroid.showcase.application.remote.RetrofitClient
import com.jordroid.showcase.quote.random.data.localdatasource.remote.QuoteApi
import com.jordroid.showcase.quote.random.domain.repository.QuoteRepository
import com.jordroid.showcase.quote.random.domain.usecase.QuoteUseCase
import com.jordroid.showcase.quote.random.presenter.view.QuoteViewModel
import com.jordroid.showcase.quote.anime.data.remote.QuoteAnimeApi
import com.jordroid.showcase.quote.anime.domain.repository.QuoteAnimeRepository
import com.jordroid.showcase.quote.anime.domain.usecase.QuoteAnimeUseCase
import com.jordroid.showcase.quote.anime.presenter.view.QuoteAnimeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

@Suppress("unused")
class QuoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@QuoteApplication)
            modules(myModule)
        }
    }
}

val myModule = module {
    // ViewModel
    viewModel { QuoteViewModel(get()) } // TODO Read documentation viewmodel koin
    viewModel { QuoteAnimeViewModel(get()) }
    // UseCase
    single { QuoteUseCase(get()) }
    single { QuoteAnimeUseCase(get()) }
    // Repository
    single { QuoteRepository(get(), get()) }
    single { QuoteAnimeRepository(get(), get()) }
    // DAO
    single { get<QuoteDatabase>().quoteDao() }
    single { get<QuoteDatabase>().quoteAnimeDao() }
    // API
    single { get<Retrofit>().create(QuoteApi::class.java) }
    single { get<Retrofit>().create(QuoteAnimeApi::class.java) }
    // Retrofit
    single { RetrofitClient.getRetrofitInstance() }
    // Database
    single { QuoteDatabase.getDatabase(get()) }
}