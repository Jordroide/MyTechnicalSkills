package com.jordroid.showcase.application.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL_ANIME = "https://animechan.vercel.app/api/"
        private const val BASE_URL_DOGS = "https://api.thedogapi.com/"

        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getRetrofitAnimeInstance(): Retrofit {
            return INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(BASE_URL_ANIME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun getRetrofitDogsInstance(): Retrofit {
            return INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(BASE_URL_DOGS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
