package com.jordroid.showcase.application

import android.app.Application
import com.jordroid.showcase.application.database.QuoteDatabase
import com.jordroid.showcase.application.remote.RetrofitClient
import com.jordroid.showcase.quote.domain.repository.QuoteRepository
import com.jordroid.showcase.quote.domain.usecase.QuoteUseCase
import com.jordroid.showcase.quote.presenter.view.QuoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

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
    single { QuoteViewModel(get()) }
    // UseCase
    single { QuoteUseCase(get()) }
    // Repository
    single { QuoteRepository(get(), get()) }
    // DAO
    single { get<QuoteDatabase>().quoteDao() }
    // Retrofit
    single { RetrofitClient.getRetrofitInstance() }
    // Database
    single { QuoteDatabase.getDatabase(get()) }
}