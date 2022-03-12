package com.jordroid.showcase.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jordroid.showcase.quotes.data.source.local.QuoteRoom
import com.jordroid.showcase.quotes.data.source.local.QuoteRoomDao
import com.jordroid.showcase.browsing.data.source.local.QuoteAnimeRoom
import com.jordroid.showcase.browsing.data.source.local.QuoteAnimeRoomDao

@Database(
    entities = [
        QuoteRoom::class,
        QuoteAnimeRoom::class
    ],
    version = 3,
    exportSchema = false
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteRoomDao

    abstract fun quoteAnimeDao(): QuoteAnimeRoomDao

    companion object {
        private const val DATABASE_NAME = "QUOTE_DATABASE"

        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
