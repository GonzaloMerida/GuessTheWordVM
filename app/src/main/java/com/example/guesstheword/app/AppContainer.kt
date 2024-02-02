package com.example.guesstheword.app

import android.content.Context
import com.example.guesstheword.repositories.WordsRepository

class AppContainer(context : Context) {

    //Repositorio de palabras.
    private val _wordsRepository : WordsRepository = WordsRepository()
    val wordsRepository get() = _wordsRepository

}