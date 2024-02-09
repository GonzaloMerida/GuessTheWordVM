package com.example.guesstheword.datamodel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Update
    fun update(word: Word)

    @Query("SELECT * FROM words ORDER BY title")
    fun getAllWords(): List<Word>?

    //coge solo una palabra aleatoria de la lista ordenada de manera aleatoria
    @Query("SELECT * FROM words ORDER BY RAND() DESC LIMIT 1")
    fun getRandomWord(): Word?

    @Query("DELETE FROM words")
    fun clearWords()

    @Query("DELETE FROM words WHERE title=:name")
    fun clearOneWord(name : String)
}