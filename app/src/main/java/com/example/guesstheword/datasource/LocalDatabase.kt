package com.example.guesstheword.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.guesstheword.datamodel.Game
import com.example.guesstheword.datamodel.GamesDAO
import com.example.guesstheword.datamodel.Word
import com.example.guesstheword.datamodel.WordsDAO

@Database(
    entities = [Word::class, Game::class],
    version = 1,
    exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun wordsDao() : WordsDAO
    abstract fun gamesDao() : GamesDAO

    companion object {
        @Volatile
        private var Instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalDatabase::class.java, "guess_the_word_database")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}