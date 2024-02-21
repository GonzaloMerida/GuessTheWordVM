package com.example.guesstheword.repositories

import androidx.room.Query
import com.example.guesstheword.datamodel.Word
import com.example.guesstheword.datamodel.WordsDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class WordsRepository (
    private val wordsDao: WordsDAO,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun insertWord(word: Word) = withContext(ioDispatcher) {
        wordsDao.insertWord(word)
    }

    suspend fun getAllWords() : List<Word>? = withContext(ioDispatcher) {
        return@withContext wordsDao.getAllWords()
    }

    suspend fun deleteWord(word : Word) = withContext(ioDispatcher) {
        wordsDao.deleteWord(word)
    }

    suspend fun getRandomWord(): Word? = withContext(ioDispatcher) {
        return@withContext wordsDao.getRandomWord()
    }

    suspend fun clearWords() = withContext(ioDispatcher) {
        wordsDao.clearWords()
    }

}