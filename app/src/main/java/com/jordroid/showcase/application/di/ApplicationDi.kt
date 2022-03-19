package com.jordroid.showcase.application.di

import com.jordroid.showcase.application.database.QuoteDatabase
import com.jordroid.showcase.application.remote.Endpoints
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    // retrofit instance
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL_ANIME)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Database instance
    single { QuoteDatabase.getDatabase(get()) }
}