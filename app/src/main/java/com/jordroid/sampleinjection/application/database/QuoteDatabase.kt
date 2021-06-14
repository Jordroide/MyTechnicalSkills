package com.jordroid.sampleinjection.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jordroid.sampleinjection.application.QuoteApplication
import com.jordroid.sampleinjection.quote.data.localdatasource.database.QuoteRoom
import com.jordroid.sampleinjection.quote.data.localdatasource.database.QuoteRoomDao

@Database(
    entities = [
        QuoteRoom::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao() : QuoteRoomDao

    companion object {
        private const val DATABASE_NAME = "QUOTE_DATABASE"

        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        fun getDatabase(context: Context): QuoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
