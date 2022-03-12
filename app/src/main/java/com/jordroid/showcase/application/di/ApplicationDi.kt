package com.jordroid.showcase.application.di

import com.jordroid.showcase.application.database.QuoteDatabase
import com.jordroid.showcase.application.remote.RetrofitClient
import org.koin.dsl.module

val applicationModule = module {
    // retrofit instance
    single { RetrofitClient.getRetrofitAnimeInstance() }

    // Database instance
    single { QuoteDatabase.getDatabase(get()) }
}