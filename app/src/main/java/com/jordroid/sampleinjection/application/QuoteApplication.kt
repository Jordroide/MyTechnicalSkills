package com.jordroid.sampleinjection.application

import android.app.Application
import com.jordroid.sampleinjection.application.database.QuoteDatabase
import com.jordroid.sampleinjection.application.remote.RetrofitClient
import com.jordroid.sampleinjection.quote.domain.repository.QuoteRepository
import com.jordroid.sampleinjection.quote.domain.usecase.QuoteUseCase
import com.jordroid.sampleinjection.quote.presenter.view.QuoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

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
    single { QuoteViewModel(get()) }
    single { QuoteUseCase(get()) }
    single { QuoteRepository(get(), get()) }
    single { get<QuoteDatabase>().quoteDao() }
    single { RetrofitClient.getRetrofitInstance() }
    single { QuoteDatabase.getDatabase(get()) }
}