package com.example.guesstheword.repositories

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.guesstheword.datamodel.Game
import com.example.guesstheword.datamodel.GamesDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GamesRepository (
    private val gamesDao: GamesDAO,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insertGame(game: Game) = withContext(ioDispatcher) {
        gamesDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) = withContext(ioDispatcher) {
        gamesDao.deleteGame(game)
    }

    suspend fun clearGames() = withContext(ioDispatcher) {
        gamesDao.clearGames()
    }

    suspend fun gelAllGames() : List<Game>? = withContext(ioDispatcher) {
        return@withContext gamesDao.getAllGames()
    }

}