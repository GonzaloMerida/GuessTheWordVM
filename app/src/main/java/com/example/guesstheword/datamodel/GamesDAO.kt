package com.example.guesstheword.datamodel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GamesDAO {
    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("SELECT * from games ORDER BY date")
    suspend fun getAllGames(): List<Game>?

    @Query("DELETE FROM games")
    suspend fun clearGames()
}