package com.jordroid.sampleinjection.quote.data.localdatasource.remote

import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {

    @GET("jokes/random")
    suspend fun getRandomQuote() : Response<QuoteDto>
}