package com.jordroid.showcase.gallery.data.remote

import com.jordroid.showcase.gallery.data.model.DogPictureDataSource
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface DoggyApi {

    @Headers("x-api-key: f44a96cb-fb70-4d90-b969-3eb1d959bba4")
    @GET()
    suspend fun getDoggoImages(
        @Url url: String,
        @Query("page") page: Int,
        @Query("limit") size: Int,
    ): List<DogPictureDataSource>
}