package com.jordroid.showcase.gallery.data.remote

import com.jordroid.showcase.gallery.data.model.DoggoDataSource
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DoggyApi {

    @Headers("x-api-key: f44a96cb-fb70-4d90-b969-3eb1d959bba4")
    @GET("v1/images/search")
    suspend fun getDoggoImages(
        @Query("page") page: Int,
        @Query("limit") size: Int,
    ): List<DoggoDataSource>
}