package com.jordroid.showcase.quotes.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {

    @GET("random")
    suspend fun getRandomQuote() : Response<QuoteDto>
}