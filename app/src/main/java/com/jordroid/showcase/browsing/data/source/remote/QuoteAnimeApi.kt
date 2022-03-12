package com.jordroid.showcase.browsing.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

interface QuoteAnimeApi {

    @GET("available/anime")
    suspend fun getAllAnime(): Response<List<String>>
}
