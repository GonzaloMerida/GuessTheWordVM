package com.example.guesstheword.dependencies

import android.content.Context
import com.example.guesstheword.datasource.LocalDatabase
import com.example.guesstheword.repositories.GamesRepository
import com.example.guesstheword.repositories.WordsRepository

class Appcontainer(context : Context) {

    //Repositorio de palabras.
    private val _wordsRepository : WordsRepository by lazy {
        WordsRepository(LocalDatabase.getDatabase(context).wordsDao())
    }
    val wordsRepository get() = _wordsRepository

    //Repositorio de juegos.
    private val _gamesRepository : GamesRepository by lazy {
        GamesRepository(LocalDatabase.getDatabase(context).gamesDao())
    }
    val gamesRepository get() = _gamesRepository

}